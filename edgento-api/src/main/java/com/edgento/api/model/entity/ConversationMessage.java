/**
 * WHAT: Entity representing a single message in a conversation.
 * WHY: Stores history of user and agent messages.
 * HOW: Mapped to conversation_messages table.
 */
package com.edgento.api.model.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "conversation_messages")
@Data
public class ConversationMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // TODO: add fields
}
