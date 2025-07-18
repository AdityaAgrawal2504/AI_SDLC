package com.example.userregistration.exception;

/**
 * Custom exception thrown when a user with a given identifier (e.g., phone number) already exists.
 */
public class UserConflictException extends RuntimeException {
    public UserConflictException(String message) {
        super(message);
    }
}

// src/main/java/com/example/userregistration/exception/ServiceException.java