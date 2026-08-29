-- WHAT: Creates the conversation_messages table.
-- WHY:  Stores every single message in every conversation — both from the user
--       and from the AI assistant. This is our conversation history.
-- HOW:  Each row belongs to one conversation via conversation_id FK.

CREATE TABLE conversation_messages (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    -- Which conversation this message belongs to
    conversation_id UUID NOT NULL REFERENCES audit_conversations(id),

    -- role: who sent this message?
    -- 'USER' = the human, 'ASSISTANT' = the AI
    -- We use these exact strings because OpenAI's API uses them too.
    role VARCHAR(20) NOT NULL,

    -- content: the full text of the message (can be long, so TEXT not VARCHAR)
    content TEXT NOT NULL,

    -- extracted_data: JSONB column storing structured facts the AI pulled from user messages.
    -- Example after TEAM_SIZE step: { "teamSize": 12 }
    -- Example after TOOLS_USED step: { "tools": ["WhatsApp", "Excel", "Tally"] }
    -- JSONB is PostgreSQL's binary JSON type — faster to query than plain JSON.
    extracted_data JSONB,

    -- step_number: which FSM step (0=INTRO, 1=TEAM_SIZE, 2=TOOLS_USED, 3=PAIN_POINTS, 4=BUDGET, 5=COMPLETE)
    step_number INTEGER NOT NULL,

    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

-- Index for fast retrieval of all messages in a conversation (very common query)
CREATE INDEX idx_messages_conversation ON conversation_messages(conversation_id);
