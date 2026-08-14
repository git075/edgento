/**
 * WHAT: Repository for AuditConversation entity.
 */
package com.edgento.api.repository;

import com.edgento.api.model.entity.AuditConversation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConversationRepository extends JpaRepository<AuditConversation, Long> {
}
