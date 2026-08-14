/**
 * WHAT: Response DTO for a Lead.
 */
package com.edgento.api.model.dto.response;

import lombok.Data;

@Data
public class LeadResponse {
    private Long id;
    private String name;
    private String email;
}
