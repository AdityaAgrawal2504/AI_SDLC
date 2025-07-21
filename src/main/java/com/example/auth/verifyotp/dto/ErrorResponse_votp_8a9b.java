package com.example.auth.verifyotp.dto;

import com.example.auth.verifyotp.config.ErrorCode_votp_8a9b;
import lombok.Data;

import java.time.Instant;
import java.time.format.DateTimeFormatter;

/**
 * Defines the standard structure for an error response.
 */
@Data
public class ErrorResponse_votp_8a9b {

    /**
     * A unique, machine-readable code identifying the specific error.
     * example: "INVALID_OTP"
     */
    private String errorCode;

    /**
     * A human-readable description of the error.
     * example: "The provided OTP is incorrect or has expired."
     */
    private String message;

    /**
     * The ISO 8601 timestamp of when the error occurred.
     * example: "2023-01-01T12:05:00Z"
     */
    private String timestamp;

    /**
     * Constructs an ErrorResponse using an ErrorCode enum.
     * @param errorCode The ErrorCode enum constant defining the error.
     */
    public ErrorResponse_votp_8a9b(ErrorCode_votp_8a9b errorCode) {
        this.errorCode = errorCode.getCode();
        this.message = errorCode.getMessage();
        this.timestamp = DateTimeFormatter.ISO_INSTANT.format(Instant.now());
    }

    /**
     * Constructs an ErrorResponse with a custom message.
     * @param errorCode The ErrorCode enum constant defining the error.
     * @param message A specific, more detailed error message.
     */
    public ErrorResponse_votp_8a9b(ErrorCode_votp_8a9b errorCode, String message) {
        this.errorCode = errorCode.getCode();
        this.message = message;
        this.timestamp = DateTimeFormatter.ISO_INSTANT.format(Instant.now());
    }
}
```
```java