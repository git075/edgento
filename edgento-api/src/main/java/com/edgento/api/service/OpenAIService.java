/*
 * WHAT: Service that handles ALL direct communication with the OpenAI API.
 * WHY:  Centralising OpenAI calls in one place means:
 *       1. If OpenAI changes their API, we only update this one file.
 *       2. AgentService stays clean — it focuses on business logic, not HTTP details.
 *       3. Easy to mock this class in unit tests.
 * HOW:  Uses the WebClient bean from OpenAIConfig to make HTTP calls.
 *       Provides three methods for three different use cases.
 *
 * 📚 CONCEPT: Reactive Programming (Mono/Flux)
 * Traditional code is synchronous: call a function → wait for result → continue.
 * Reactive code is non-blocking: call a function → get a "promise" (Mono/Flux) →
 * the result will arrive later and trigger the next step automatically.
 * - Mono<T>: a stream of 0 or 1 item (like a single API response)
 * - Flux<T>: a stream of 0 to N items (like streaming tokens from OpenAI)
 */
package com.edgento.api.service;

import com.edgento.api.exception.AgentProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Map;

@Service
@Slf4j  // Lombok: generates a 'log' field for logging (log.info(), log.error(), etc.)
@RequiredArgsConstructor
public class OpenAIService {

    // The pre-configured WebClient bean (used ONLY for streaming)
    private final WebClient openAiWebClient;

    // The pre-configured RestClient bean (used for blocking calls)
    private final org.springframework.web.client.RestClient openAiRestClient;

    // The model name bean from OpenAIConfig (e.g., "gpt-4o-mini")
    @Qualifier("openAiModel")
    private final String openAiModel;

    // Jackson ObjectMapper: converts Java objects ↔ JSON strings.
    // 📚 CONCEPT: Jackson is the most popular Java JSON library. Spring Boot auto-configures it.
    private final ObjectMapper objectMapper;

    // ─────────────────────────────────────────────────────────────────────────
    // METHOD 1: Extract Structured Data
    // ─────────────────────────────────────────────────────────────────────────
    /**
     * WHAT: Sends a user message to OpenAI and forces it to return a specific JSON structure.
     * WHY:  At each FSM step, we need to extract facts (like teamSize=12) from free-form user
     *       text. Without structured outputs, the AI might say "about twelve" instead of 12.
     *       OpenAI's Structured Outputs guarantees the response matches our JSON schema exactly.
     * HOW:  We send the message with a response_format of type "json_schema".
     *       The AI MUST return JSON that matches the schema — no exceptions.
     *
     * @param userMessage  The user's message text
     * @param systemPrompt Instruction for the AI on how to extract the data
     * @param jsonSchema   JSON Schema string defining what structure we expect back
     * @return JSON string of the extracted data, e.g. {"teamSize": 12}
     */
    public String extractStructuredData(String userMessage, String systemPrompt, String jsonSchema) {
        try {
            // Build the request body as a Jackson ObjectNode (a JSON object in Java)
            ObjectNode requestBody = objectMapper.createObjectNode();
            requestBody.put("model", openAiModel);

            // Build the messages array: [{"role": "system", ...}, {"role": "user", ...}]
            ArrayNode messages = requestBody.putArray("messages");
            messages.addObject().put("role", "system").put("content", systemPrompt);
            messages.addObject().put("role", "user").put("content", userMessage);

            // 📚 CONCEPT: JSON Schema + Structured Outputs
            // We tell OpenAI: "I want the response as JSON that strictly matches this schema."
            // The 'strict: true' field is the key — it forces compliance, no hallucinated fields.
            ObjectNode responseFormat = requestBody.putObject("response_format");
            responseFormat.put("type", "json_schema");
            ObjectNode jsonSchemaNode = responseFormat.putObject("json_schema");
            jsonSchemaNode.put("name", "extraction_schema");
            jsonSchemaNode.put("strict", true);
            // Parse the schema string into a JsonNode and embed it
            jsonSchemaNode.set("schema", objectMapper.readTree(jsonSchema));

            // Make the synchronous HTTP call using RestClient.
            // WHY RestClient? WebClient.block() locks a thread in the reactive thread pool,
            // which can exhaust resources. RestClient is designed for blocking IO on Tomcat threads.
            String responseBody = openAiRestClient
                    .post()
                    .uri("/chat/completions")
                    .body(requestBody)
                    .retrieve()
                    .body(String.class);

            // Parse the response and extract the content from the first choice
            JsonNode responseJson = objectMapper.readTree(responseBody);
            return responseJson
                    .path("choices")
                    .get(0)
                    .path("message")
                    .path("content")
                    .asText();

        } catch (Exception e) {
            log.error("OpenAI structured data extraction failed: {}", e.getMessage());
            throw new AgentProcessingException("Failed to extract structured data from AI: " + e.getMessage());
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
    // METHOD 2: Streaming Chat Completion (for real-time token streaming)
    // ─────────────────────────────────────────────────────────────────────────
    /**
     * WHAT: Calls OpenAI with stream=true and returns each token as it arrives.
     * WHY:  This powers the "typing" animation on the frontend. Without streaming, the user
     *       would see a blank screen for 3-5 seconds, then the whole response appears at once.
     *       With streaming, they see each word appear in real time — much better UX.
     * HOW:  OpenAI sends the response as a series of SSE events. Each event contains one
     *       JSON chunk with a token. We parse each chunk and emit the token into a Flux.
     *
     * @param messages     Full conversation history (alternating USER/ASSISTANT messages)
     * @param systemPrompt The AI's persona and instructions
     * @return Flux<String> of individual tokens as they stream from OpenAI
     */
    public Flux<String> streamChatCompletion(List<Map<String, String>> messages, String systemPrompt) {
        try {
            ObjectNode requestBody = objectMapper.createObjectNode();
            requestBody.put("model", openAiModel);
            requestBody.put("stream", true);  // <-- This is the key: enables streaming

            ArrayNode messagesArray = requestBody.putArray("messages");
            // Add system prompt as first message
            messagesArray.addObject().put("role", "system").put("content", systemPrompt);
            // Add conversation history
            for (Map<String, String> msg : messages) {
                messagesArray.addObject()
                        .put("role", msg.get("role"))
                        .put("content", msg.get("content"));
            }

            return openAiWebClient
                    .post()
                    .uri("/chat/completions")
                    .bodyValue(requestBody)
                    .retrieve()
                    // bodyToFlux(String.class) reads the stream line by line
                    .bodyToFlux(String.class)
                    // Each line from OpenAI looks like: "data: {...}" or "data: [DONE]"
                    .filter(line -> line.startsWith("data: ") && !line.equals("data: [DONE]"))
                    // Extract the actual JSON from "data: {...}"
                    .map(line -> line.substring(6))
                    // Parse the JSON and get the token content from delta
                    .flatMap(jsonChunk -> {
                        try {
                            JsonNode chunk = objectMapper.readTree(jsonChunk);
                            String token = chunk
                                    .path("choices").get(0)
                                    .path("delta")
                                    .path("content")
                                    .asText("");
                            return token.isEmpty() ? Flux.empty() : Flux.just(token);
                        } catch (Exception e) {
                            return Flux.empty(); // Skip malformed chunks silently
                        }
                    })
                    .onErrorMap(e -> new AgentProcessingException("OpenAI stream failed: " + e.getMessage()));

        } catch (Exception e) {
            log.error("Failed to build streaming request: {}", e.getMessage());
            return Flux.error(new AgentProcessingException("Stream setup failed: " + e.getMessage()));
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
    // METHOD 3: Blocking Chat Completion (for report generation)
    // ─────────────────────────────────────────────────────────────────────────
    /**
     * WHAT: Standard non-streaming OpenAI call. Waits for the full response.
     * WHY:  Report generation needs the entire response in one go so we can parse it
     *       as a complete JSON object. Streaming a report doesn't make sense here.
     * HOW:  Same as extractStructuredData but without the JSON Schema constraint.
     *       Used for generating the final AuditReport content.
     *
     * @param messages     Full conversation history
     * @param systemPrompt Instructions for the report generation
     * @return Full response string from OpenAI (the complete report JSON)
     */
    public String chatCompletion(List<Map<String, String>> messages, String systemPrompt) {
        try {
            ObjectNode requestBody = objectMapper.createObjectNode();
            requestBody.put("model", openAiModel);
            // Force JSON output for report generation (without strict schema — more flexible)
            requestBody.putObject("response_format").put("type", "json_object");

            ArrayNode messagesArray = requestBody.putArray("messages");
            messagesArray.addObject().put("role", "system").put("content", systemPrompt);
            for (Map<String, String> msg : messages) {
                messagesArray.addObject()
                        .put("role", msg.get("role"))
                        .put("content", msg.get("content"));
            }

            String responseBody = openAiRestClient
                    .post()
                    .uri("/chat/completions")
                    .body(requestBody)
                    .retrieve()
                    .body(String.class);

            JsonNode responseJson = objectMapper.readTree(responseBody);
            return responseJson
                    .path("choices").get(0)
                    .path("message")
                    .path("content")
                    .asText();

        } catch (Exception e) {
            log.error("OpenAI chat completion failed: {}", e.getMessage());
            throw new AgentProcessingException("Failed to get AI response: " + e.getMessage());
        }
    }
}
