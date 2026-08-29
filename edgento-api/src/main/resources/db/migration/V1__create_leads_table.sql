-- WHAT: Migration script to create the leads table.
-- WHY:  We need a place to store contact info gathered before or during an audit.
-- HOW:  Flyway will automatically run this script on startup.

-- UUID primary key: We use UUID instead of auto-increment integers because:
-- 1. UUIDs are globally unique (safe for distributed systems)
-- 2. They don't reveal how many records exist (security)
-- 3. They can be generated client-side without hitting the database
CREATE TABLE leads (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    company_name VARCHAR(255),
    phone VARCHAR(50),
    source_page VARCHAR(100),
    utm_source VARCHAR(100),
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

-- Index on email for fast lookups (e.g., checking if a lead already exists)
CREATE INDEX idx_leads_email ON leads(email);
