package com.example.auth.exception;

/**
 * Enumeration of standardized error codes for the application.
 */
public enum ErrorCode {
    VALIDATION_ERROR,
    INVALID_CREDENTIALS,
    ACCOUNT_LOCKED,
    RATE_LIMIT_EXCEEDED,
    OTP_SERVICE_FAILURE,
    INTERNAL_SERVER_ERROR,
    RESOURCE_NOT_FOUND
}
```
src/main/java/com/example/auth/exception/ApplicationException.java
```java