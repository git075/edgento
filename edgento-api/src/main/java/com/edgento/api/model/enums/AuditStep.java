/**
 * WHAT: Enum representing the step within an audit process.
 * WHY: Guides the AI agent on what information to ask for next.
 * HOW: State transition controlled by the FSM.
 */
package com.edgento.api.model.enums;

public enum AuditStep {
    GATHERING_INFO,
    ANALYZING,
    REPORT_GENERATION,
    COMPLETED
}
