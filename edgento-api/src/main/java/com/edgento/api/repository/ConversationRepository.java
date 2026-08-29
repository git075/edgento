/*
 * WHAT: Spring Data JPA Repository for AuditConversation entity.
 * WHY:  Gives us all CRUD operations + a custom query to find by lead email.
 */
package com.edgento.api.repository;

import com.edgento.api.model.entity.AuditConversation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ConversationRepository extends JpaRepository<AuditConversation, UUID> {
}
