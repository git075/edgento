/*
 * WHAT: Response DTO for an ongoing conversation state.
 * WHY:  Returns the current FSM step, status, AI message, and any extracted data
 *       back to the frontend after each user message.
 */
package com.edgento.api.model.dto.response;

import com.edgento.api.model.enums.AuditStep;
import com.edgento.api.model.enums.ConversationStatus;

import java.util.UUID;

public record ConversationResponse(
        UUID conversationId,
        AuditStep currentStep,
        ConversationStatus status,
        String message,
        Object extractedData  // Object because the shape varies per step
) {}
