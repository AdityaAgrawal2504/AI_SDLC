package com.example.userregistration.exception;

/**
 * Custom exception for general service-level errors, such as database connectivity issues.
 */
public class ServiceException extends RuntimeException {
    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}

// src/main/java/com/example/userregistration/service/IUserRegistrationService.java