/**
 * WHAT: Exception for missing resources.
 */
package com.edgento.api.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
