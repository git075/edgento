-- Create audit_reports table
CREATE TABLE audit_reports (
    id BIGSERIAL PRIMARY KEY,
    conversation_id BIGINT REFERENCES audit_conversations(id) UNIQUE,
    report_content TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
