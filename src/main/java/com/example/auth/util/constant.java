package com.example.auth.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * Defines standardized error codes for the application.
 * Each enum constant maps to an HTTP status and a default message.
 */
@Getter
@RequiredArgsConstructor
public enum ErrorCodeAUTH {
    // 400 Bad Request
    VALIDATION_ERROR(HttpStatus.BAD_REQUEST, "The request body failed validation."),
    FIELD_REQUIRED(HttpStatus.BAD_REQUEST, "A required field is missing."),
    INVALID_DATA_TYPE(HttpStatus.BAD_REQUEST, "A field has an invalid data type."),
    INVALID_PHONE_FORMAT(HttpStatus.BAD_REQUEST, "Phone number must be exactly 10 digits."),
    PASSWORD_TOO_SHORT(HttpStatus.BAD_REQUEST, "Password must be at least 8 characters long."),

    // 401 Unauthorized
    INVALID_CREDENTIALS(HttpStatus.UNAUTHORIZED, "The phone number or password you entered is incorrect."),

    // 403 Forbidden
    ACCOUNT_LOCKED(HttpStatus.FORBIDDEN, "The user account has been temporarily locked due to too many failed login attempts."),

    // 429 Too Many Requests
    RATE_LIMIT_EXCEEDED(HttpStatus.TOO_MANY_REQUESTS, "The client has made too many requests in a given amount of time."),

    // 500 Internal Server Error
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred on the server."),
    
    // 503 Service Unavailable
    OTP_SERVICE_UNAVAILABLE(HttpStatus.SERVICE_UNAVAILABLE, "The downstream service for sending OTPs is currently unavailable.");

    private final HttpStatus httpStatus;
    private final String defaultMessage;
}
```
```java
// src/main/java/com/example/auth/dto/InitiateLoginRequestAUTH.java