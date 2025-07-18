package com.example.auth.exception;

import com.example.auth.dto.ApiError;
import com.example.auth.util.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Base exception for all custom API-related errors.
 */
@Getter
public class ApiException extends RuntimeException {

    private final HttpStatus httpStatus;
    private final ApiError apiError;

    public ApiException(ErrorCode errorCode) {
        super(errorCode.getDefaultMessage());
        this.httpStatus = errorCode.getHttpStatus();
        this.apiError = new ApiError(errorCode);
    }

    public ApiException(ErrorCode errorCode, String message) {
        super(message);
        this.httpStatus = errorCode.getHttpStatus();
        this.apiError = new ApiError(errorCode.getCode(), message);
    }
}

<!-- src/main/java/com/example/auth/exception/LoginAttemptNotFoundException.java -->