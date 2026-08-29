/*
 * WHAT: Entity representing a Sales Lead.
 * WHY:  Persists lead information gathered before or during an audit.
 * HOW:  Mapped to the 'leads' table in PostgreSQL using JPA.
 */
package com.edgento.api.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

// 📚 CONCEPT: JPA Entities - Plain Old Java Objects (POJOs) mapped to database tables using JPA annotations.
@Entity // Specifies that the class is an entity and is mapped to a database table.
@Table(name = "leads") // Specifies the exact name of the database table to map to.
@Getter
@Setter
@Builder
@NoArgsConstructor // Lombok auto-generates a no-argument constructor (required by JPA).
@AllArgsConstructor // Lombok auto-generates a constructor with all arguments.
public class Lead {

    @Id // Specifies the primary key of an entity.
    @GeneratedValue(strategy = GenerationType.AUTO) // Let Hibernate handle UUID generation if we create a new instance
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(name = "company_name") // Map camelCase field to snake_case column
    private String companyName;

    private String phone;

    @Column(name = "source_page")
    private String sourcePage;

    @Column(name = "utm_source")
    private String utmSource;

    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    // 📚 CONCEPT: Entity Lifecycle Callbacks
    // This method runs automatically right before the entity is saved to the database for the first time.
    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = OffsetDateTime.now();
        }
    }
}
