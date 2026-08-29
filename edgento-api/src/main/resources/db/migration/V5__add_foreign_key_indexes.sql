-- WHAT: Adds database indexes to foreign keys to prevent table scans.
-- WHY:  In PostgreSQL, foreign keys do NOT automatically get indexes.
--       When we run queries like "SELECT * FROM conversation_messages WHERE conversation_id = X",
--       the database has to scan every single row in the messages table (a Sequential Scan)
--       unless there is an index. As the table grows, this gets extremely slow.
-- HOW:  CREATE INDEX creates a B-Tree index on the specified column.

-- 1. Index for finding all conversations for a specific lead
CREATE INDEX idx_audit_conversations_lead_id ON audit_conversations(lead_id);

-- 2. Index for finding all messages belonging to a conversation (Heavily used in FSM step recovery)
CREATE INDEX idx_conversation_messages_conversation_id ON conversation_messages(conversation_id);

-- 3. Index for finding the final report for a conversation
CREATE INDEX idx_audit_reports_conversation_id ON audit_reports(conversation_id);
