/**
 * WHAT: Response DTO for an Audit Report.
 */
package com.edgento.api.model.dto.response;

import lombok.Data;

@Data
public class AuditReportResponse {
    private Long id;
    private String content;
}
