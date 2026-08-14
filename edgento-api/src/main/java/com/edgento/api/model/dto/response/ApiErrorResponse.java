/**
 * WHAT: Standardized error response object.
 * WHY: Provides a consistent structure for error responses from the API.
 */
package com.edgento.api.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiErrorResponse {
    private int status;
    private String message;
    private String details;
}
