/**
 * WHAT: Service for managing AI Agent interactions.
 * WHY: Coordinates between the user inputs, the LLM, and the FSM state.
 */
package com.edgento.api.service;

import com.edgento.api.model.dto.request.StartAuditRequest;
import com.edgento.api.model.dto.request.SendMessageRequest;
import com.edgento.api.model.dto.response.ConversationResponse;
import org.springframework.stereotype.Service;

@Service
public class AgentService {

    public ConversationResponse startAudit(StartAuditRequest request) {
        // TODO: implement logic
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public ConversationResponse processMessage(Long conversationId, SendMessageRequest request) {
        // TODO: implement logic
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
