/*
 * WHAT: Standardized error response object.
 * WHY:  Provides a consistent JSON structure for all API errors.
 *       The frontend can rely on this exact shape every time a 4xx or 5xx occurs.
 */
package com.edgento.api.model.dto.response;

import java.time.OffsetDateTime;

public record ApiErrorResponse(
        int status,
        String error,
        String message,
        String timestamp
) {
    // 📚 CONCEPT: Static Factory Method
    // Instead of using 'new ApiErrorResponse(...)', we provide a helper method
    // that automatically fills in the current timestamp.
    public static ApiErrorResponse of(int status, String error, String message) {
        return new ApiErrorResponse(status, error, message, OffsetDateTime.now().toString());
    }
}
