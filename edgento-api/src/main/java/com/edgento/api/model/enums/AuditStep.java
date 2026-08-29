/*
 * WHAT: Enum representing each step in the AI Business Diagnostic FSM.
 * WHY:  The diagnostic agent uses a Finite State Machine (FSM) with 6 states.
 *       Using an enum makes transitions type-safe and impossible to misspell.
 *       The compiler will catch any typo at compile time rather than at runtime.
 * HOW:  The AgentService reads the current step from AuditConversation.currentStep
 *       and decides what question to ask next and what data to extract.
 *       Each value is stored in the DB as its name string (e.g., "INTRO")
 *       because we use @Enumerated(EnumType.STRING) on the entity field.
 *
 * 📚 CONCEPT: Finite State Machine (FSM)
 * An FSM is a model with a finite number of states and strict rules for
 * transitioning between them. Think of a traffic light: RED → GREEN → YELLOW → RED.
 * Our agent does: INTRO → TEAM_SIZE → TOOLS_USED → PAIN_POINTS → BUDGET → COMPLETE.
 * At each step, the agent collects ONE specific piece of information before moving on.
 * This prevents the AI from going off-topic or skipping questions.
 *
 * STEP RESPONSIBILITIES:
 * - INTRO:        Get business name + industry from user
 * - TEAM_SIZE:    Get number of employees/team members
 * - TOOLS_USED:   Get list of tools/software currently in use
 * - PAIN_POINTS:  Get list of operational problems and challenges
 * - BUDGET:       Get monthly budget for tools/software
 * - COMPLETE:     All data collected — triggers AuditReport generation. Terminal state.
 *
 * WHY NOT a String field?
 * If we stored the step as a plain String, we could accidentally write
 * "TEAMSIZE" or "team_size" anywhere in the code — typos that only crash at runtime.
 * With an enum, misspelling is a compile error caught immediately.
 */
package com.edgento.api.model.enums;

public enum AuditStep {
    INTRO,        // Step 0: Greeting + get business name & industry
    TEAM_SIZE,    // Step 1: How many people on the team?
    TOOLS_USED,   // Step 2: What tools/software does the team use?
    PAIN_POINTS,  // Step 3: What are the biggest operational problems?
    BUDGET,       // Step 4: What is the monthly budget for tooling?
    COMPLETE      // Step 5: All data collected — AuditReport generated. No further transitions.
}
