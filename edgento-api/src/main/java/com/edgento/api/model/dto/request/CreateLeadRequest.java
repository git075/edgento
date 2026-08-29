/*
 * WHAT: DTO (Data Transfer Object) for creating a new lead.
 * WHY:  We use a separate DTO instead of accepting the Lead entity directly because:
 *       1. Entities have auto-generated fields (id, createdAt) that clients shouldn't set.
 *       2. API contract should be stable even if the entity changes.
 *       3. We can validate input fields independently of the entity.
 * HOW:  Client sends this JSON body to POST /api/v1/leads.
 *       Spring deserializes it into this record, validation runs, then LeadService converts it.
 *
 * 📚 CONCEPT: Java Records
 * Records (introduced in Java 16) are immutable data classes. Instead of writing
 * a class with getters, setters, equals, hashCode, and toString, you declare:
 *   record CreateLeadRequest(String name, String email) {}
 * Java generates all of that automatically. Perfect for DTOs.
 */
package com.edgento.api.model.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

// 📚 CONCEPT: Jakarta Validation
// These annotations (@NotBlank, @Email, @Size) trigger automatic validation
// when @Valid is used in the controller. If validation fails, Spring returns
// a 400 Bad Request before the service even runs.
public record CreateLeadRequest(
        @NotBlank(message = "Name is required")
        @Size(max = 255, message = "Name must be under 255 characters")
        String name,

        @NotBlank(message = "Email is required")
        @Email(message = "Email must be a valid email address")
        String email,

        String companyName,   // Optional — not annotated, can be null
        String phone,         // Optional
        String sourcePage,    // Optional — URL path (e.g., "/services")
        String utmSource      // Optional — marketing attribution (e.g., "google")
) {}
