/**
 * WHAT: Repository for AuditReport entity.
 */
package com.edgento.api.repository;

import com.edgento.api.model.entity.AuditReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditReportRepository extends JpaRepository<AuditReport, Long> {
}
