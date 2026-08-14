/**
 * WHAT: Entity representing a Sales Lead.
 * WHY: Persists lead information gathered before or during an audit.
 * HOW: Mapped to the 'leads' table in PostgreSQL.
 */
package com.edgento.api.model.entity;

// 📚 CONCEPT: JPA Entities - Plain Old Java Objects (POJOs) mapped to database tables using JPA annotations.
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity // Specifies that the class is an entity and is mapped to a database table.
@Table(name = "leads") // Specifies the name of the database table to be used for mapping.
@Data // Lombok annotation to auto-generate getters, setters, toString, equals, and hashCode methods.
@NoArgsConstructor // Lombok annotation to auto-generate a no-argument constructor (required by JPA).
@AllArgsConstructor // Lombok annotation to auto-generate a constructor with all arguments.
public class Lead {

    @Id // Specifies the primary key of an entity.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Provides the generation strategy for the primary key (auto-increment in Postgres).
    private Long id;

    private String name;
    private String email;
    private String company;
}
