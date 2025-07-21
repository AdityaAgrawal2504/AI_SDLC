package com.example.auth.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Enumeration of standardized error codes and messages for the application.
 */
@Getter
public enum ErrorCodeAUTH {
    INVALID_INPUT_FORMAT("INVALID_INPUT_FORMAT", "The request format is incorrect or violates validation rules.", HttpStatus.BAD_REQUEST),
    INVALID_CREDENTIALS("INVALID_CREDENTIALS", "The phone number or password provided is incorrect.", HttpStatus.UNAUTHORIZED),
    ACCOUNT_LOCKED("ACCOUNT_LOCKED", "Account is locked due to too many failed login attempts.", HttpStatus.FORBIDDEN),
    RATE_LIMIT_EXCEEDED("RATE_LIMIT_EXCEEDED", "Too many login requests. Please try again later.", HttpStatus.TOO_MANY_REQUESTS),
    OTP_SERVICE_FAILURE("OTP_SERVICE_FAILURE", "Failed to send OTP. Please try again.", HttpStatus.INTERNAL_SERVER_ERROR),
    INTERNAL_SERVER_ERROR("INTERNAL_SERVER_ERROR", "An unexpected internal server error occurred.", HttpStatus.INTERNAL_SERVER_ERROR);

    private final String code;
    private final String message;
    private final HttpStatus httpStatus;

    ErrorCodeAUTH(String code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
```
```java
// src/main/java/com/example/auth/exception/ApplicationExceptionAUTH.java