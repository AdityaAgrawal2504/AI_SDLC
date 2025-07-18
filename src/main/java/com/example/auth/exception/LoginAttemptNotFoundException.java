package com.example.auth.exception;

import com.example.auth.util.ErrorCode;

/**
 * Exception thrown when a login attempt cannot be found, is expired, or already used.
 */
public class LoginAttemptNotFoundException extends ApiException {
    public LoginAttemptNotFoundException() {
        super(ErrorCode.LOGIN_ATTEMPT_NOT_FOUND);
    }
}

<!-- src/main/java/com/example/auth/exception/InvalidOtpException.java -->