/*
 * WHAT: JPA Entity representing the final AI-generated business audit report.
 * WHY:  After all 5 FSM steps are complete, the AI synthesizes everything into
 *       a structured report. This table stores that report permanently.
 * HOW:  @OneToOne with AuditConversation — exactly one report per conversation.
 *       JSONB fields store arrays (vulnerabilities, recommendations) as JSON strings.
 */
package com.edgento.api.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "audit_reports")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuditReport {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    // 📚 CONCEPT: @OneToOne vs @ManyToOne
    // @OneToOne means this entity is the "one" side of a one-to-one relationship.
    // One AuditReport belongs to exactly one AuditConversation, and vice versa.
    // The UNIQUE constraint on conversation_id in the DB enforces this at the database level.
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conversation_id", nullable = false)
    private AuditConversation conversation;

    // health_score: 0-100. A 62 means "62% healthy" — there's significant room to improve.
    @Column(name = "health_score", nullable = false)
    private Integer healthScore;

    // vulnerabilities: stored as a JSONB string, e.g.: ["No CRM", "Manual invoicing"]
    // AgentService serializes List<String> → JSON string before saving.
    @Column(name = "vulnerabilities", nullable = false, columnDefinition = "jsonb")
    private String vulnerabilities;

    // revenueGapEstimate: human-readable, e.g.: "₹28,000/month"
    @Column(name = "revenue_gap_estimate")
    private String revenueGapEstimate;

    // recommendations: stored as JSONB string, e.g.: ["Use Zoho CRM", "Automate invoicing"]
    @Column(name = "recommendations", nullable = false, columnDefinition = "jsonb")
    private String recommendations;

    @Column(name = "generated_at", nullable = false, updatable = false)
    private OffsetDateTime generatedAt;

    @PrePersist
    protected void onCreate() {
        if (generatedAt == null) {
            generatedAt = OffsetDateTime.now();
        }
    }
}
