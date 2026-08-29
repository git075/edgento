/*
 * WHAT: JPA Entity representing one AI Diagnostic Audit Conversation.
 * WHY:  Every time a user starts the AI agent, we create a row here to track
 *       where they are in the 5-step FSM (Finite State Machine) and their status.
 * HOW:  Mapped to the 'audit_conversations' table. Has a Many-to-One relationship
 *       with Lead (one lead can start many conversations over time).
 */
package com.edgento.api.model.entity;

import com.edgento.api.model.enums.AuditStep;
import com.edgento.api.model.enums.ConversationStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "audit_conversations")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuditConversation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    // 📚 CONCEPT: @ManyToOne
    // This creates a foreign key relationship. "Many" conversations can belong
    // to "One" lead. @JoinColumn specifies which column in THIS table holds the FK.
    // We use LAZY loading: the Lead is only fetched from DB when we actually access it,
    // not automatically when we load the conversation. This is more efficient.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lead_id", nullable = false)
    private Lead lead;

    // 📚 CONCEPT: @Enumerated(EnumType.STRING)
    // Tells JPA to store the enum as its name (e.g., "INTRO") instead of its ordinal
    // (0, 1, 2...). Always use STRING — if you ever reorder the enum, ordinal values
    // break silently, which is a very hard bug to find.
    @Enumerated(EnumType.STRING)
    @Column(name = "current_step", nullable = false)
    private AuditStep currentStep;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ConversationStatus status;

    @Column(name = "started_at", nullable = false, updatable = false)
    private OffsetDateTime startedAt;

    @Column(name = "completed_at")
    private OffsetDateTime completedAt;

    @PrePersist
    protected void onCreate() {
        if (startedAt == null) {
            startedAt = OffsetDateTime.now();
        }
        if (currentStep == null) {
            currentStep = AuditStep.INTRO;
        }
        if (status == null) {
            status = ConversationStatus.ACTIVE;
        }
    }
}
