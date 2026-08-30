-- V6__create_contact_messages_table.sql
-- WHAT: Creates a table to store contact form submissions.

CREATE TABLE contact_messages (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    message TEXT NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL
);

-- Index on email for searching/analytics later
CREATE INDEX idx_contact_messages_email ON contact_messages(email);
