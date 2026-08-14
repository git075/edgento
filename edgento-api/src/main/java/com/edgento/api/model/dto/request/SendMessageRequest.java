/**
 * WHAT: DTO for sending a message to the agent.
 */
package com.edgento.api.model.dto.request;

import lombok.Data;

@Data
public class SendMessageRequest {
    private String message;
    // TODO: add fields
}
