package com.example.auth.exception;

import com.example.auth.util.ErrorCode;

/**
 * Exception thrown when the provided OTP is incorrect.
 */
public class InvalidOtpException extends ApiException {
    public InvalidOtpException() {
        super(ErrorCode.INVALID_OTP);
    }
}

<!-- src/main/java/com/example/auth/exception/TooManyAttemptsException.java -->