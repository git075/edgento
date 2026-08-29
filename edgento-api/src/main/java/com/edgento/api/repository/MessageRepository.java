/*
 * WHAT: Spring Data JPA Repository for ConversationMessage entity.
 * WHY:  We need custom queries to:
 *       1. Get all messages in a conversation (for building OpenAI history).
 *       2. Find the most recent USER message (for updating extracted data).
 *
 * 📚 CONCEPT: Spring Data JPA Method Name Queries
 * Spring Data JPA can generate SQL queries automatically just from the method name!
 * findByConversationOrderByStepNumber → SELECT * FROM conversation_messages
 *                                        WHERE conversation_id = ? ORDER BY step_number
 * findTopByConversationAndRoleOrderByCreatedAtDesc → SELECT * ... ORDER BY created_at DESC LIMIT 1
 */
package com.edgento.api.repository;

import com.edgento.api.model.entity.AuditConversation;
import com.edgento.api.model.entity.ConversationMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MessageRepository extends JpaRepository<ConversationMessage, UUID> {

    // Get all messages for a conversation, in step order (for building OpenAI history)
    List<ConversationMessage> findByConversationOrderByStepNumber(AuditConversation conversation);

    // Find the last USER message (for updating extracted data after extraction)
    Optional<ConversationMessage> findTopByConversationAndRoleOrderByCreatedAtDesc(
            AuditConversation conversation, String role);

    // Count messages in a conversation (used for rate limiting)
    long countByConversation(AuditConversation conversation);
}
