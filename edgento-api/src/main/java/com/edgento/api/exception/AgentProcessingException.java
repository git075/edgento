/**
 * WHAT: Exception for failures in AI agent processing.
 */
package com.edgento.api.exception;

public class AgentProcessingException extends RuntimeException {
    public AgentProcessingException(String message) {
        super(message);
    }
}
