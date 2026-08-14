/*
 * WHAT: REST controller handling all HTTP requests for the AI Diagnostic Agent.
 * WHY:  This is the entry point for starting a conversation, sending messages,
 *       getting the SSE stream, and retrieving the final report.
 * HOW:  Delegates all complex business and AI logic to AgentService.
 *
 * 📚 CONCEPT: Server-Sent Events (SSE)
 * For the stream endpoint, we return a Flux<ServerSentEvent<String>>.
 * This keeps the HTTP connection open and pushes data from the server
 * to the client token-by-token as OpenAI generates it.
 */
package com.edgento.api.controller;

import com.edgento.api.model.dto.request.SendMessageRequest;
import com.edgento.api.model.dto.request.StartAuditRequest;
import com.edgento.api.model.dto.response.AuditReportResponse;
import com.edgento.api.model.dto.response.ConversationResponse;
import com.edgento.api.service.AgentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/agent")
@RequiredArgsConstructor
public class AgentController {

    private final AgentService agentService;

    // POST /api/v1/agent/start
    // Starts a new audit conversation.
    @PostMapping("/start")
    public ResponseEntity<ConversationResponse> startAudit(@Valid @RequestBody StartAuditRequest request) {
        ConversationResponse response = agentService.startAudit(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // POST /api/v1/agent/{conversationId}/message
    // Sends a user message to an ongoing conversation.
    // @PathVariable extracts the {conversationId} from the URL.
    @PostMapping("/{conversationId}/message")
    public ResponseEntity<ConversationResponse> sendMessage(
            @PathVariable UUID conversationId,
            @Valid @RequestBody SendMessageRequest request) {
        ConversationResponse response = agentService.processMessage(conversationId, request);
        return ResponseEntity.ok(response);
    }

    // GET /api/v1/agent/{conversationId}/stream
    // The SSE endpoint. Produces text/event-stream.
    @GetMapping(value = "/{conversationId}/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> streamResponse(@PathVariable UUID conversationId) {
        // TODO: Phase B - Connect to AgentService stream
        return Flux.empty();
    }

    // GET /api/v1/agent/{conversationId}/report
    // Retrieves the final generated report.
    @GetMapping("/{conversationId}/report")
    public ResponseEntity<AuditReportResponse> getReport(@PathVariable UUID conversationId) {
        AuditReportResponse response = agentService.getReport(conversationId);
        return ResponseEntity.ok(response);
    }
}
