/*
 * WHAT: JPA Entity representing a single message in an audit conversation.
 * WHY:  We store every USER and ASSISTANT message so we can:
 *       1. Send full conversation history to OpenAI at each step (for context).
 *       2. Store the structured data extracted from each user message (for the final report).
 * HOW:  Mapped to 'conversation_messages'. Many messages belong to one conversation.
 */
package com.edgento.api.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "conversation_messages")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConversationMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conversation_id", nullable = false)
    private AuditConversation conversation;

    // role: "USER" or "ASSISTANT" — matches OpenAI's own terminology
    @Column(name = "role", nullable = false)
    private String role;

    // content: full message text. TEXT type in DB allows unlimited length.
    // @Column(columnDefinition = "TEXT") maps the Java String to a SQL TEXT column.
    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;

    // extractedData: the JSON string of structured data the AI pulled from user messages.
    // We store as String in Java because JPA doesn't natively support JSONB —
    // the actual JSONB column type is defined in the Flyway migration.
    // In future we could use a custom AttributeConverter to map to a Map<String,Object>.
    @Column(name = "extracted_data", columnDefinition = "jsonb")
    private String extractedData;

    // stepNumber: integer representing which FSM step this message belongs to.
    // 0=INTRO, 1=TEAM_SIZE, 2=TOOLS_USED, 3=PAIN_POINTS, 4=BUDGET, 5=COMPLETE
    @Column(name = "step_number", nullable = false)
    private Integer stepNumber;

    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = OffsetDateTime.now();
        }
    }
}
