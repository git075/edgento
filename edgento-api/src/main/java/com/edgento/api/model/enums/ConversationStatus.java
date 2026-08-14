/**
 * WHAT: Enum representing the status of an audit conversation.
 * WHY: Tracks the lifecycle state of a conversation in the FSM (Finite State Machine).
 * HOW: Used in AuditConversation entity.
 */
package com.edgento.api.model.enums;

// 📚 CONCEPT: Enums for FSM - Using enums ensures type safety when representing strict states.
public enum ConversationStatus {
    STARTED,      // Initial state when user starts an audit
    IN_PROGRESS,  // Actively chatting with the agent
    COMPLETED,    // Audit finished, report generated
    ABANDONED     // User left without completing
}
