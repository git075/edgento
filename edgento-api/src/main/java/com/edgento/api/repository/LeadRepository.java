/**
 * WHAT: Repository for Lead entity.
 * WHY: Provides basic CRUD operations and query methods for Leads in the database.
 * HOW: Extends JpaRepository which Spring Data JPA automatically implements at runtime.
 */
package com.edgento.api.repository;

import com.edgento.api.model.entity.Lead;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// 📚 CONCEPT: @Repository - Marks the interface as a Data Access Object (DAO) that encapsulates storage, retrieval, and search behavior.
@Repository
public interface LeadRepository extends JpaRepository<Lead, Long> {
}
