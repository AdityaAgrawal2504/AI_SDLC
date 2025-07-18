package com.example.auth.dto;

import com.example.auth.util.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a standardized API error response.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiError {

    /**
     * A unique code identifying the error type.
     */
    private String errorCode;

    /**
     * A descriptive message about the error.
     */
    private String errorMessage;

    public ApiError(ErrorCode errorCode) {
        this.errorCode = errorCode.getCode();
        this.errorMessage = errorCode.getDefaultMessage();
    }
     public ApiError(ErrorCode errorCode, String customMessage) {
        this.errorCode = errorCode.getCode();
        this.errorMessage = customMessage;
    }
}

<!-- src/main/java/com/example/auth/exception/ApiException.java -->