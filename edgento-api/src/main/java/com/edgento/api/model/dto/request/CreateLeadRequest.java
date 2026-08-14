/**
 * WHAT: DTO for creating a lead.
 * WHY: Encapsulates incoming request data for lead creation.
 * HOW: Validated by Spring Validation.
 */
package com.edgento.api.model.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateLeadRequest {
    @NotBlank
    private String name;
    
    @Email
    @NotBlank
    private String email;
}
