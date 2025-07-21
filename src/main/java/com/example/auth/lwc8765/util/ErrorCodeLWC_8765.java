package com.example.auth.lwc8765.util;

/**
 * Enumeration of standardized error codes for the API.
 */
public enum ErrorCodeLWC_8765 {
    INVALID_INPUT,
    INVALID_CREDENTIALS,
    ACCOUNT_INACTIVE,
    ACCOUNT_LOCKED,
    RATE_LIMIT_EXCEEDED,
    OTP_SERVICE_FAILURE,
    INTERNAL_SERVER_ERROR
}
```
```java
// src/main/java/com/example/auth/lwc8765/dto/request/LoginCredentialsRequestLWC_8765.java