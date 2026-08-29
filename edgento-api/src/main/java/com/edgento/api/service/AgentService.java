/*
 * WHAT: The core business logic service for the AI Diagnostic Agent.
 * WHY:  This is the brain of the entire Edgento product. It:
 *       1. Creates and manages audit conversations.
 *       2. Enforces the 5-step FSM (Finite State Machine).
 *       3. Persists all messages and extracted data to the database.
 *       4. Orchestrates OpenAI calls for data extraction and question generation.
 *       5. Generates the final AuditReport when the conversation completes.
 * HOW:  Uses AgentService → OpenAIService (for AI) + Repositories (for DB).
 *       The FSM is implemented as a switch expression on the current AuditStep enum.
 */
package com.edgento.api.service;

import com.edgento.api.exception.AgentProcessingException;
import com.edgento.api.exception.ResourceNotFoundException;
import com.edgento.api.mapper.ConversationMapper;
import com.edgento.api.model.dto.request.SendMessageRequest;
import com.edgento.api.model.dto.request.StartAuditRequest;
import com.edgento.api.model.dto.response.AuditReportResponse;
import com.edgento.api.model.dto.response.ConversationResponse;
import com.edgento.api.model.entity.*;
import com.edgento.api.model.enums.AuditStep;
import com.edgento.api.model.enums.ConversationStatus;
import com.edgento.api.repository.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;

import java.time.OffsetDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class AgentService {

    private final LeadRepository leadRepository;
    private final ConversationRepository conversationRepository;
    private final MessageRepository messageRepository;
    private final AuditReportRepository auditReportRepository;
    private final OpenAIService openAIService;
    private final ConversationMapper conversationMapper;
    private final ObjectMapper objectMapper;

    // ─────────────────────────────────────────────────────────────────────────
    // FSM CONFIGURATION: Step-to-step definitions
    // ─────────────────────────────────────────────────────────────────────────

    // The system prompt for the AI agent — defines its persona and rules
    private static final String AGENT_SYSTEM_PROMPT = """
            You are the Edgento Business Diagnostic AI, a friendly and insightful business analyst.
            Your goal is to help Indian SMB owners identify operational inefficiencies and revenue leakage.

            You are conducting a structured 5-step audit. Ask questions in a conversational, warm,
            and professional tone. Keep questions concise. Use Indian business context (mention tools
            like Tally, Zoho, WhatsApp Business, Vyapar where relevant as examples).

            Do NOT ask multiple questions at once. Focus on ONE thing per step.
            Do NOT make up numbers or statistics. Be genuinely helpful.
            """;

    // Mapping from each step to the JSON Schema for data extraction
    // 📚 CONCEPT: Java Text Blocks (""" ... """)
    // A modern Java feature (Java 15+) for multi-line strings. Much cleaner than
    // "string1" + "string2" concatenation for writing JSON/SQL/HTML inside Java.
    private static final Map<AuditStep, String> STEP_SCHEMAS = Map.of(
        AuditStep.INTRO, """
            {
              "type": "object",
              "properties": {
                "businessName": { "type": "string" },
                "industry": { "type": "string" }
              },
              "required": ["businessName", "industry"],
              "additionalProperties": false
            }
            """,
        AuditStep.TEAM_SIZE, """
            {
              "type": "object",
              "properties": {
                "teamSize": { "type": "integer" }
              },
              "required": ["teamSize"],
              "additionalProperties": false
            }
            """,
        AuditStep.TOOLS_USED, """
            {
              "type": "object",
              "properties": {
                "tools": { "type": "array", "items": { "type": "string" } }
              },
              "required": ["tools"],
              "additionalProperties": false
            }
            """,
        AuditStep.PAIN_POINTS, """
            {
              "type": "object",
              "properties": {
                "painPoints": { "type": "array", "items": { "type": "string" } }
              },
              "required": ["painPoints"],
              "additionalProperties": false
            }
            """,
        AuditStep.BUDGET, """
            {
              "type": "object",
              "properties": {
                "monthlyBudget": { "type": "string" }
              },
              "required": ["monthlyBudget"],
              "additionalProperties": false
            }
            """
    );

    // Extraction system prompts for each step
    private static final Map<AuditStep, String> EXTRACTION_PROMPTS = Map.of(
        AuditStep.INTRO,
            "Extract the business name and industry from the user's message. " +
            "If not clearly stated, make a reasonable inference.",
        AuditStep.TEAM_SIZE,
            "Extract the team or employee count as an integer from the user's message. " +
            "If they say 'about 12' or 'around 10-15', pick the closest integer.",
        AuditStep.TOOLS_USED,
            "Extract all tools, software, apps, or platforms mentioned by the user as an array of strings.",
        AuditStep.PAIN_POINTS,
            "Extract all problems, challenges, or pain points mentioned by the user as an array of strings.",
        AuditStep.BUDGET,
            "Extract the monthly budget or spending amount as a string. " +
            "Preserve the currency symbol and format (e.g., '₹15,000/month')."
    );

    // ─────────────────────────────────────────────────────────────────────────
    // PUBLIC METHODS
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Starts a new audit conversation.
     * 1. Creates (or finds) a Lead record.
     * 2. Creates an AuditConversation at step INTRO.
     * 3. Gets the AI's opening message (blocking — needed immediately).
     * 4. Saves the AI message to DB.
     * 5. Returns the ConversationResponse.
     */
    @Transactional // All DB operations in this method happen in one database transaction
    public ConversationResponse startAudit(StartAuditRequest request) {
        // Step 1: Find existing lead by email or create a new one
        // WHY: We don't want duplicate leads if the same person starts multiple audits.
        Lead lead = leadRepository.findByEmail(request.visitorEmail())
                .orElseGet(() -> {
                    Lead newLead = new Lead();
                    newLead.setName(request.visitorName());
                    newLead.setEmail(request.visitorEmail());
                    newLead.setSourcePage("/agent");
                    return leadRepository.save(newLead);
                });

        // Step 2: Create the conversation
        AuditConversation conversation = AuditConversation.builder()
                .lead(lead)
                .currentStep(AuditStep.INTRO)
                .status(ConversationStatus.ACTIVE)
                .build();
        conversation = conversationRepository.save(conversation);

        // Step 3: Generate the opening message using the AI
        String openingPrompt = String.format(
            "The user's name is %s. Greet them warmly and ask the INTRO step question: " +
            "What's the name of their business and what industry are they in? " +
            "Keep it to 2-3 sentences max.",
            request.visitorName()
        );

        String openingMessage = openAIService.chatCompletion(
                List.of(), // No history yet
                AGENT_SYSTEM_PROMPT + "\n\n" + openingPrompt
        );

        // Step 4: Save the AI's opening message to DB
        saveMessage(conversation, "ASSISTANT", openingMessage, null, 0);

        log.info("Started audit conversation {} for lead {}", conversation.getId(), lead.getEmail());
        return conversationMapper.toResponse(conversation, openingMessage, null);
    }

    /**
     * Processes a user's message for a given conversation step.
     * This is the heart of the FSM — it advances the state machine.
     */
    @Transactional
    public ConversationResponse processMessage(UUID conversationId, SendMessageRequest request) {
        // Load the conversation (throws 404 if not found)
        AuditConversation conversation = conversationRepository.findById(conversationId)
                .orElseThrow(() -> new ResourceNotFoundException("Conversation not found: " + conversationId));

        // Guard: don't process messages on completed/abandoned conversations
        if (conversation.getStatus() != ConversationStatus.ACTIVE) {
            throw new AgentProcessingException("Conversation is not active. Status: " + conversation.getStatus());
        }

        // Guard: limit messages to 10 per conversation to prevent API credit drain by bots
        long messageCount = messageRepository.countByConversation(conversation);
        if (messageCount >= 10) {
            throw new AgentProcessingException("Message limit reached for this conversation.");
        }

        AuditStep currentStep = conversation.getCurrentStep();
        int stepNumber = currentStep.ordinal(); // INTRO=0, TEAM_SIZE=1, etc.

        // Step 1: Save the user's message
        saveMessage(conversation, "USER", request.content(), null, stepNumber);

        // Step 2: Extract structured data from the user's message (if not COMPLETE)
        String extractedJson = null;
        if (currentStep != AuditStep.COMPLETE) {
            String schema = STEP_SCHEMAS.get(currentStep);
            String extractionPrompt = EXTRACTION_PROMPTS.get(currentStep);
            extractedJson = openAIService.extractStructuredData(
                    request.content(), extractionPrompt, schema
            );
            log.info("Extracted data at step {}: {}", currentStep, extractedJson);
        }

        // Step 3: Update the message with extracted data
        // (We re-fetch the last user message and update it)
        updateLastUserMessageExtractedData(conversation, extractedJson);

        // Step 4: Advance the FSM to the next step
        AuditStep nextStep = getNextStep(currentStep);
        conversation.setCurrentStep(nextStep);

        String nextMessage;

        // Step 5: If COMPLETE, commit DB state FIRST, then trigger report generation separately.
        // WHY separate? generateAuditReport() makes a live OpenAI network call (5-8 seconds).
        // If we call it inside this @Transactional method, the DB connection stays open for
        // those 8 seconds. If OpenAI fails, @Transactional rolls back and the conversation
        // gets stuck in a broken state forever. Instead: commit the COMPLETED status first,
        // then fire the report generation in a separate, independent async operation.
        if (nextStep == AuditStep.COMPLETE) {
            conversation.setStatus(ConversationStatus.COMPLETED);
            conversation.setCompletedAt(OffsetDateTime.now());
            conversationRepository.save(conversation);
            // triggerReportGeneration runs AFTER this method returns (post-commit).
            // It has its own @Transactional boundary, so a failure there cannot
            // roll back the COMPLETED status we just saved.
            triggerReportGeneration(conversation.getId());
            nextMessage = "Your business audit is complete! 🎉 I've generated your personalised " +
                          "Edgento Business Health Report. You can view it now.";
        } else {
            // Step 6: Get the AI's next question
            conversationRepository.save(conversation);
            List<Map<String, String>> history = buildMessageHistory(conversation);
            nextMessage = getNextQuestion(nextStep, history);
            saveMessage(conversation, "ASSISTANT", nextMessage, null, nextStep.ordinal());
        }

        return conversationMapper.toResponse(conversation, nextMessage, extractedJson);
    }

    /**
     * Returns a Flux<String> of streaming tokens for the NEXT AI question.
     * This is called by AgentController's SSE endpoint.
     */
    public Flux<String> streamNextResponse(UUID conversationId) {
        AuditConversation conversation = conversationRepository.findById(conversationId)
                .orElseThrow(() -> new ResourceNotFoundException("Conversation not found: " + conversationId));

        List<Map<String, String>> history = buildMessageHistory(conversation);
        return openAIService.streamChatCompletion(history, AGENT_SYSTEM_PROMPT);
    }

    /**
     * Retrieves the final audit report for a completed conversation.
     */
    public AuditReportResponse getReport(UUID conversationId) {
        AuditReport report = auditReportRepository.findByConversationId(conversationId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Report not found for conversation: " + conversationId));
        return conversationMapper.toReportResponse(report);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // PRIVATE HELPERS
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Advances the FSM to the next step.
     * 📚 CONCEPT: switch expression (Java 14+)
     * Instead of if/else chains, a switch expression maps each value to a result.
     * The -> syntax means: "for this case, return this value."
     */
    private AuditStep getNextStep(AuditStep current) {
        return switch (current) {
            case INTRO       -> AuditStep.TEAM_SIZE;
            case TEAM_SIZE   -> AuditStep.TOOLS_USED;
            case TOOLS_USED  -> AuditStep.PAIN_POINTS;
            case PAIN_POINTS -> AuditStep.BUDGET;
            case BUDGET      -> AuditStep.COMPLETE;
            case COMPLETE    -> AuditStep.COMPLETE; // Terminal state — stays here
        };
    }

    /**
     * Generates the next question for a given FSM step using the conversation history.
     */
    private String getNextQuestion(AuditStep step, List<Map<String, String>> history) {
        String stepInstruction = switch (step) {
            case TEAM_SIZE   -> "Ask how many people are on their team (employees/contractors).";
            case TOOLS_USED  -> "Ask what tools, software, or apps they currently use in the business day-to-day.";
            case PAIN_POINTS -> "Ask what their biggest operational pain points or challenges are.";
            case BUDGET      -> "Ask what their monthly budget is for business tools and software.";
            default          -> "Continue the conversation naturally.";
        };
        return openAIService.chatCompletion(history, AGENT_SYSTEM_PROMPT + "\n\nNext task: " + stepInstruction);
    }

    /**
     * Triggers report generation asynchronously, in a new independent transaction.
     *
     * @Async:  Spring runs this in a background thread from the task executor pool.
     *          The caller (processMessage) does NOT wait for this — it returns immediately.
     *          This means the user gets a fast response, and the report generates in the background.
     *
     * Propagation.REQUIRES_NEW:  Even though processMessage has @Transactional,
     *          this method creates its own SEPARATE transaction. If OpenAI fails and this
     *          method throws, only THIS transaction rolls back — the COMPLETED status
     *          from processMessage is already safely committed and untouched.
     */
    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void triggerReportGeneration(UUID conversationId) {
        AuditConversation conversation = conversationRepository.findById(conversationId)
                .orElseThrow(() -> new ResourceNotFoundException("Conversation not found: " + conversationId));
        generateAuditReport(conversation);
    }

    /**
     * Generates the final AuditReport by sending all collected data to OpenAI.
     * Called from triggerReportGeneration — runs in its own transaction.
     */
    private void generateAuditReport(AuditConversation conversation) {
        // Collect all extracted data from conversation messages
        List<ConversationMessage> messages = messageRepository.findByConversationOrderByStepNumber(conversation);
        Map<String, Object> allExtractedData = new HashMap<>();

        for (ConversationMessage msg : messages) {
            if (msg.getExtractedData() != null && !msg.getExtractedData().isBlank()) {
                try {
                    Map<String, Object> stepData = objectMapper.readValue(
                            msg.getExtractedData(), new TypeReference<>() {});
                    allExtractedData.putAll(stepData);
                } catch (Exception e) {
                    log.warn("Could not parse extracted data for message {}", msg.getId());
                }
            }
        }

        String reportPrompt = String.format("""
            Based on the following business audit data, generate a comprehensive business health report.
            
            Business Data: %s
            
            Return a JSON object with exactly these fields:
            {
              "healthScore": <integer 0-100>,
              "vulnerabilities": [<array of specific problem strings>],
              "revenueGapEstimate": "<string like '₹25,000/month'>",
              "recommendations": [<array of specific actionable recommendation strings>]
            }
            
            Be specific and reference the actual data provided. Make the health score realistic.
            Vulnerabilities and recommendations should each have 3-5 items.
            """, objectMapper.valueToTree(allExtractedData).toString());

        String reportJson = openAIService.chatCompletion(List.of(), reportPrompt);

        try {
            JsonNode reportNode = objectMapper.readTree(reportJson);
            AuditReport report = AuditReport.builder()
                    .conversation(conversation)
                    .healthScore(reportNode.path("healthScore").asInt())
                    .vulnerabilities(reportNode.path("vulnerabilities").toString())
                    .revenueGapEstimate(reportNode.path("revenueGapEstimate").asText())
                    .recommendations(reportNode.path("recommendations").toString())
                    .build();
            auditReportRepository.save(report);
            log.info("Generated audit report for conversation {}", conversation.getId());
        } catch (Exception e) {
            log.error("Failed to parse or save audit report: {}", e.getMessage());
            throw new AgentProcessingException("Failed to generate audit report: " + e.getMessage());
        }
    }

    /**
     * Saves a message to the database.
     */
    private void saveMessage(AuditConversation conversation, String role,
                             String content, String extractedData, int stepNumber) {
        ConversationMessage message = ConversationMessage.builder()
                .conversation(conversation)
                .role(role)
                .content(content)
                .extractedData(extractedData)
                .stepNumber(stepNumber)
                .build();
        messageRepository.save(message);
    }

    /**
     * Finds the most recent USER message and updates its extracted data.
     */
    private void updateLastUserMessageExtractedData(AuditConversation conversation, String extractedData) {
        messageRepository.findTopByConversationAndRoleOrderByCreatedAtDesc(conversation, "USER")
                .ifPresent(msg -> {
                    msg.setExtractedData(extractedData);
                    messageRepository.save(msg);
                });
    }

    /**
     * Builds the conversation history in the format OpenAI expects:
     * List of { "role": "user"/"assistant", "content": "..." }
     */
    private List<Map<String, String>> buildMessageHistory(AuditConversation conversation) {
        return messageRepository.findByConversationOrderByStepNumber(conversation)
                .stream()
                .map(msg -> Map.of(
                        "role", msg.getRole().toLowerCase(),
                        "content", msg.getContent()
                ))
                .collect(Collectors.toList());
    }
}
