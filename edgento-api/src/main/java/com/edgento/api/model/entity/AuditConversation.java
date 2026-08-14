/**
 * WHAT: Entity representing an audit conversation session.
 * WHY: Tracks user interactions with the agent.
 * HOW: Mapped to audit_conversations table.
 */
package com.edgento.api.model.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "audit_conversations")
@Data
public class AuditConversation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // TODO: add fields for state, lead reference, etc.
}
