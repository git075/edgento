/*
 * WHAT: DTO for starting an audit conversation.
 * WHY:  Captures the visitor's name and email to create or link a Lead record.
 */
package com.edgento.api.model.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record StartAuditRequest(
        @NotBlank(message = "Visitor name is required")
        String visitorName,

        @NotBlank(message = "Visitor email is required")
        @Email(message = "Must be a valid email address")
        String visitorEmail
) {}
