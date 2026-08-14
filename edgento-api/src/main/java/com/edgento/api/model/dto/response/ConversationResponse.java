/**
 * WHAT: Response DTO for a conversation update.
 */
package com.edgento.api.model.dto.response;

import lombok.Data;

@Data
public class ConversationResponse {
    private Long id;
    private String replyMessage;
}
