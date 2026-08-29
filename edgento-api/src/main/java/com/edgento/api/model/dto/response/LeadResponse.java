/*
 * WHAT: DTO representing a Lead response from the API.
 * WHY:  We never return JPA Entities directly to the client. This record defines
 *       the exact JSON structure we want the client to see.
 */
package com.edgento.api.model.dto.response;

import java.time.OffsetDateTime;
import java.util.UUID;

public record LeadResponse(
        UUID id,
        String name,
        String email,
        String companyName,
        OffsetDateTime createdAt
) {}
