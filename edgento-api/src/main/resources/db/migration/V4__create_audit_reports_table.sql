-- WHAT: Creates the audit_reports table.
-- WHY:  After a conversation completes all 5 FSM steps, the AI generates a final
--       executive-level business audit report. This table stores that report.
-- HOW:  One report per conversation (enforced by UNIQUE constraint on conversation_id).

CREATE TABLE audit_reports (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    -- UNIQUE: enforces the business rule that one conversation → at most one report
    conversation_id UUID NOT NULL UNIQUE REFERENCES audit_conversations(id),

    -- health_score: 0-100 score representing the business's operational health
    health_score INTEGER NOT NULL,

    -- vulnerabilities: JSONB array of identified problem strings
    -- Example: ["No CRM system", "Manual invoicing via Excel"]
    vulnerabilities JSONB NOT NULL,

    -- revenue_gap_estimate: a human-readable estimate of money being lost
    -- Example: "₹28,000/month"
    revenue_gap_estimate VARCHAR(100),

    -- recommendations: JSONB array of actionable fix suggestions
    -- Example: ["Implement Zoho CRM free tier", "Move to Vyapar for invoicing"]
    recommendations JSONB NOT NULL,

    generated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);
