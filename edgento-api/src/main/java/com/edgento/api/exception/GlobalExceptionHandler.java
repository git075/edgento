/**
 * WHAT: Global Exception Handler.
 * WHY: Provides centralized exception handling across all @RequestMapping methods.
 *      Prevents Spring from returning HTML error pages or leaking stack traces.
 * HOW: Uses @ControllerAdvice to intercept exceptions thrown anywhere in the application.
 */
package com.edgento.api.exception;

import com.edgento.api.model.dto.response.ApiErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.stream.Collectors;

// 📚 CONCEPT: @ControllerAdvice
// A special Spring annotation that acts as an interceptor for exceptions.
// When ANY controller throws an exception, Spring routes it here instead of crashing.
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleNotFound(ResourceNotFoundException ex) {
        return ResponseEntity.status(404)
                .body(ApiErrorResponse.of(404, "Not Found", ex.getMessage()));
    }

    @ExceptionHandler(AgentProcessingException.class)
    public ResponseEntity<ApiErrorResponse> handleAgentError(AgentProcessingException ex) {
        return ResponseEntity.status(500)
                .body(ApiErrorResponse.of(500, "Processing Error", ex.getMessage()));
    }

    // Handles @Valid validation failures from Request DTOs
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidation(MethodArgumentNotValidException ex) {
        String msg = ex.getBindingResult().getFieldErrors().stream()
                .map(e -> e.getField() + ": " + e.getDefaultMessage())
                .collect(Collectors.joining(", "));
        return ResponseEntity.status(400)
                .body(ApiErrorResponse.of(400, "Validation Failed", msg));
    }

    // 🛡️ SECURITY: The Catch-All Handler
    // NEVER let a raw Exception reach the client. It exposes internal Java paths and stack traces.
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleGenericError(Exception ex) {
        // Log the full stack trace on the SERVER where it's safe
        log.error("Unhandled exception occurred", ex); 
        
        // Return a clean, safe, generic error to the CLIENT
        return ResponseEntity.status(500)
                .body(ApiErrorResponse.of(500, "Internal Server Error", 
                        "An unexpected error occurred. Please try again later."));
    }
}
