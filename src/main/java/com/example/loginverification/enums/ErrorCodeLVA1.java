package com.example.loginverification.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * Enumeration of machine-readable error codes and their corresponding HTTP status and messages.
 */
@Getter
@RequiredArgsConstructor
public enum ErrorCodeLVA1 {

    VALIDATION_ERROR(HttpStatus.BAD_REQUEST, "VALIDATION_ERROR", "One or more input fields failed validation."),
    INVALID_OTP(HttpStatus.UNAUTHORIZED, "INVALID_OTP", "The One-Time Password provided is incorrect."),
    OTP_EXPIRED(HttpStatus.UNAUTHORIZED, "OTP_EXPIRED", "The One-Time Password has expired."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER_NOT_FOUND", "The specified phone number was not found or has no pending login."),
    TOO_MANY_ATTEMPTS(HttpStatus.TOO_MANY_REQUESTS, "TOO_MANY_ATTEMPTS", "Too many verification attempts. Please try again later."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "An internal server error occurred.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
```
```java
// src/main/java/com/example/loginverification/dto/LoginVerificationRequestLVA1.java