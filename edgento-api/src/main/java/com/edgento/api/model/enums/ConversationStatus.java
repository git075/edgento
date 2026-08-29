/*
 * WHAT: Enum representing the lifecycle status of an AuditConversation.
 * WHY:  We need to know whether a conversation is currently happening,
 *       finished, or was abandoned mid-way. Using an enum prevents typos
 *       and makes the valid states explicitly clear to any developer.
 * HOW:  Stored as a VARCHAR in the DB via @Enumerated(EnumType.STRING).
 *       AgentService checks this before processing any new message —
 *       it rejects messages on non-ACTIVE conversations.
 *
 * STATUS LIFECYCLE:
 *   ACTIVE → conversation is ongoing, user is answering questions
 *   COMPLETED → all 5 FSM steps are done, AuditReport has been generated
 *   ABANDONED → user stopped mid-conversation (no activity for >30 min in future)
 *
 * WHY ONLY 3 STATES?
 * The previous version had STARTED and IN_PROGRESS as separate states.
 * These were redundant — a conversation is either happening (ACTIVE),
 * done (COMPLETED), or given up (ABANDONED). Extra states add complexity
 * without adding information.
 */
package com.edgento.api.model.enums;

public enum ConversationStatus {
    ACTIVE,     // Conversation is ongoing — user is still answering questions
    COMPLETED,  // All 5 FSM steps done — AuditReport has been generated
    ABANDONED   // User stopped mid-conversation (no activity)
}
