package com.example.userregistration.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Map;

/**
 * Defines the standardized structure for API error responses.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {

    /**
     * A machine-readable code for the specific error.
     */
    private String errorCode;

    /**
     * A human-readable message explaining the error.
     */
    private String message;

    /**
     * An optional object containing field-specific error messages.
     */
    private Map<String, String> details;
}

// src/main/java/com/example/userregistration/entity/User.java