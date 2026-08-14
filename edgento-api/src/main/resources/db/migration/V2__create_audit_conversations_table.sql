-- Create audit_conversations table
CREATE TABLE audit_conversations (
    id BIGSERIAL PRIMARY KEY,
    lead_id BIGINT REFERENCES leads(id),
    status VARCHAR(50) NOT NULL,
    current_step VARCHAR(50) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
