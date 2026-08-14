/**
 * WHAT: DTO for the contact form.
 */
package com.edgento.api.model.dto.request;

import lombok.Data;

@Data
public class ContactRequest {
    private String name;
    private String email;
    private String message;
}
