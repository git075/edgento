/*
 * WHAT: Spring Data JPA Repository for AuditReport entity.
 * WHY:  We need to find a report by conversation ID (not just by report ID).
 */
package com.edgento.api.repository;

import com.edgento.api.model.entity.AuditReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AuditReportRepository extends JpaRepository<AuditReport, UUID> {

    // Spring Data auto-generates: SELECT * FROM audit_reports WHERE conversation_id = ?
    Optional<AuditReport> findByConversationId(UUID conversationId);
}
