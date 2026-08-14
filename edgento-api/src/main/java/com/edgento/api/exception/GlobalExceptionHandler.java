/**
 * WHAT: Global Exception Handler.
 * WHY: Provides centralized exception handling across all @RequestMapping methods.
 * HOW: Uses @ControllerAdvice to intercept exceptions thrown by controllers.
 */
package com.edgento.api.exception;

import com.edgento.api.model.dto.response.ApiErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

// 📚 CONCEPT: @ControllerAdvice - Specialization of @Component for classes that declare @ExceptionHandler methods to be shared across multiple @Controller classes.
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleNotFound(ResourceNotFoundException ex) {
        return new ResponseEntity<>(new ApiErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage(), "Resource not found"), HttpStatus.NOT_FOUND);
    }
    
    // TODO: implement handlers for other exceptions
}
