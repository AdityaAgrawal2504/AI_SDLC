package com.example.auth.verifyotp.config;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Enumeration of standardized error codes used across the application.
 * Each enum constant encapsulates a machine-readable code, a human-readable message,
 * and the corresponding HTTP status for the error response.
 */
@Getter
public enum ErrorCode_votp_8a9b {
    // 400 Bad Request
    INVALID_INPUT_SESSION_TOKEN("INVALID_INPUT_SESSION_TOKEN", "Session token is missing, empty, or in an invalid format.", HttpStatus.BAD_REQUEST),
    INVALID_INPUT_OTP("INVALID_INPUT_OTP", "OTP is missing or does not conform to the required 6-digit format.", HttpStatus.BAD_REQUEST),

    // 401 Unauthorized
    INVALID_OTP("INVALID_OTP", "The provided OTP is incorrect.", HttpStatus.UNAUTHORIZED),
    OTP_EXPIRED("OTP_EXPIRED", "The provided OTP has expired.", HttpStatus.UNAUTHORIZED),
    MAX_ATTEMPTS_REACHED("MAX_ATTEMPTS_REACHED", "Maximum verification attempts have been exceeded for this session.", HttpStatus.UNAUTHORIZED),

    // 404 Not Found
    SESSION_NOT_FOUND("SESSION_NOT_FOUND", "The verification session is invalid, expired, or has already been completed.", HttpStatus.NOT_FOUND),

    // 500 Internal Server Error
    TOKEN_GENERATION_FAILED("TOKEN_GENERATION_FAILED", "The server failed to generate the access token.", HttpStatus.INTERNAL_SERVER_ERROR),
    DATABASE_ERROR("DATABASE_ERROR", "A database error occurred while verifying the session.", HttpStatus.INTERNAL_SERVER_ERROR),
    UNEXPECTED_ERROR("UNEXPECTED_ERROR", "An unexpected error occurred.", HttpStatus.INTERNAL_SERVER_ERROR);


    private final String code;
    private final String message;
    private final HttpStatus httpStatus;

    /**
     * Constructs an ErrorCode enum constant.
     * @param code The machine-readable error code string.
     * @param message The human-readable error description.
     * @param httpStatus The associated HTTP status code.
     */
    ErrorCode_votp_8a9b(String code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
```
```java