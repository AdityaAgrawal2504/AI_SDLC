package com.example.auth.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * Enumeration of standardized API error codes.
 * This provides a single source of truth for error types, improving maintainability.
 */
@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    BAD_REQUEST("BAD_REQUEST", "The request is malformed or contains invalid data.", HttpStatus.BAD_REQUEST),
    INVALID_OTP("INVALID_OTP", "The provided OTP is incorrect.", HttpStatus.UNAUTHORIZED),
    LOGIN_ATTEMPT_NOT_FOUND("LOGIN_ATTEMPT_NOT_FOUND", "The login attempt is invalid, expired, or already used.", HttpStatus.NOT_FOUND),
    TOO_MANY_ATTEMPTS("TOO_MANY_ATTEMPTS", "Maximum OTP verification attempts exceeded.", HttpStatus.TOO_MANY_REQUESTS),
    INTERNAL_SERVER_ERROR("INTERNAL_SERVER_ERROR", "An unexpected error occurred on the server.", HttpStatus.INTERNAL_SERVER_ERROR);

    private final String code;
    private final String defaultMessage;
    private final HttpStatus httpStatus;
}


<!-- src/main/java/com/example/auth/dto/VerifyOtpRequest.java -->