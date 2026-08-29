/*
 * WHAT: LeadRepository now needs a findByEmail method for AgentService.
 * WHY:  When a user starts an audit, we check if their email already exists
 *       as a lead so we don't create duplicate records.
 */
package com.edgento.api.repository;

import com.edgento.api.model.entity.Lead;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface LeadRepository extends JpaRepository<Lead, UUID> {

    // Spring Data auto-generates: SELECT * FROM leads WHERE email = ? LIMIT 1
    Optional<Lead> findByEmail(String email);
}
