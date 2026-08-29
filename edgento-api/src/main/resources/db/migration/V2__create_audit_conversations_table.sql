-- WHAT: Creates the audit_conversations table.
-- WHY:  Each time a lead starts an AI diagnostic session, one row is created here.
--       It acts as the "container" for a full conversation.
-- HOW:  Flyway runs this automatically when Spring Boot starts.

CREATE TABLE audit_conversations (
    -- UUID primary key: globally unique, does not reveal record count, safe to expose in URLs
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    -- FK to leads: every conversation belongs to exactly one lead
    lead_id UUID NOT NULL REFERENCES leads(id),

    -- current_step: tracks where we are in the 5-step FSM
    -- Valid values: INTRO, TEAM_SIZE, TOOLS_USED, PAIN_POINTS, BUDGET, COMPLETE
    current_step VARCHAR(50) NOT NULL DEFAULT 'INTRO',

    -- status: lifecycle of this conversation
    -- Valid values: ACTIVE, COMPLETED, ABANDONED
    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',

    -- started_at: TIMESTAMPTZ means timezone-aware. Always use this, never plain TIMESTAMP.
    started_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),

    -- completed_at: NULL until the conversation reaches COMPLETE status
    completed_at TIMESTAMPTZ
);
