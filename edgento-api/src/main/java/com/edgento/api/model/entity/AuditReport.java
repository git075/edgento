/**
 * WHAT: Entity representing the final audit report.
 * WHY: Stores the generated report to present to the user.
 * HOW: Mapped to audit_reports table.
 */
package com.edgento.api.model.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "audit_reports")
@Data
public class AuditReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // TODO: add fields
}
