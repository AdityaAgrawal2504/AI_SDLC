package com.example.auth.exception;

import com.example.auth.util.ErrorCode;

/**
 * Exception thrown when the maximum number of OTP verification attempts is exceeded.
 */
public class TooManyAttemptsException extends ApiException {
    public TooManyAttemptsException() {
        super(ErrorCode.TOO_MANY_ATTEMPTS);
    }
}


<!-- src/main/java/com/example/auth/model/LoginAttempt.java -->