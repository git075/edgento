/*
 * WHAT: REST Controller for the AI Diagnostic Agent endpoints.
 * WHY:  Routes HTTP requests to AgentService. Handles SSE streaming via WebFlux.
 * HOW:  Uses @RestController + @RequestMapping for REST, and Flux<String> for SSE.
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

    // POST /api/v1/agent/start — Start a new audit conversation
    @PostMapping("/start")
    public ResponseEntity<ConversationResponse> startAudit(
            @Valid @RequestBody StartAuditRequest request) {
        ConversationResponse response = agentService.startAudit(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // POST /api/v1/agent/{conversationId}/message — Send user reply
    @PostMapping("/{conversationId}/message")
    public ResponseEntity<ConversationResponse> sendMessage(
            @PathVariable UUID conversationId,
            @Valid @RequestBody SendMessageRequest request) {
        ConversationResponse response = agentService.processMessage(conversationId, request);
        return ResponseEntity.ok(response);
    }

    // GET /api/v1/agent/{conversationId}/stream — SSE stream of AI tokens
    // 📚 CONCEPT: MediaType.TEXT_EVENT_STREAM_VALUE
    // This tells Spring to keep the HTTP connection open and push data chunks
    // as they arrive, instead of closing after a single response.
    // The browser's EventSource API reads this stream natively.
    @GetMapping(value = "/{conversationId}/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> streamResponse(@PathVariable UUID conversationId) {
        return agentService.streamNextResponse(conversationId);
    }

    // GET /api/v1/agent/{conversationId}/report — Get the final audit report
    @GetMapping("/{conversationId}/report")
    public ResponseEntity<AuditReportResponse> getReport(@PathVariable UUID conversationId) {
        AuditReportResponse response = agentService.getReport(conversationId);
        return ResponseEntity.ok(response);
    }
}
