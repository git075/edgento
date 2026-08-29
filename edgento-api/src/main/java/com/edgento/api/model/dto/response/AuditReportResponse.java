/*
 * WHAT: Response DTO for the final AI-generated audit report.
 */
package com.edgento.api.model.dto.response;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public record AuditReportResponse(
        UUID reportId,
        UUID conversationId,
        Integer healthScore,
        List<String> vulnerabilities,
        String revenueGapEstimate,
        List<String> recommendations,
        OffsetDateTime generatedAt
) {}
