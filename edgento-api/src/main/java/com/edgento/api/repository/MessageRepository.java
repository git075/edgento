/**
 * WHAT: Repository for ConversationMessage entity.
 */
package com.edgento.api.repository;

import com.edgento.api.model.entity.ConversationMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<ConversationMessage, Long> {
}
