/*
 * WHAT: DTO for sending a user message to an ongoing audit conversation.
 *
 * WHY are validation annotations here and not in the Service?
 * Spring MVC validates this object BEFORE it even reaches the Service layer.
 * If the content is blank or too long, Spring automatically returns a 400 Bad Request
 * via our GlobalExceptionHandler — we never pay for an OpenAI call.
 *
 * This is called "fail early" — reject invalid input at the boundary of the system,
 * as soon as it arrives, before doing any expensive work.
 */
package com.edgento.api.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SendMessageRequest(
        @NotBlank(message = "Message content cannot be empty")
        @Size(
            max = 500,
            // WHY 500 chars? The FSM questions require short factual answers.
            // A real answer like "Zoho, Tally, WhatsApp, around 12 people" is <100 chars.
            // 500 chars allows a generous human response but stops prompt injection attacks
            // that rely on pasting thousands of characters of malicious instructions.
            message = "Message cannot exceed 500 characters"
        )
        String content
) {}

