package com.example.messaging.enums;

/**
 * Enumeration of machine-readable error codes for the API.
 */
public enum ErrorCodeSendMessageApi {
    VALIDATION_ERROR,
    AUTHENTICATION_FAILURE,
    PERMISSION_DENIED,
    RECIPIENT_NOT_FOUND,
    IDEMPOTENCY_CONFLICT,
    RATE_LIMIT_EXCEEDED,
    SERVICE_UNAVAILABLE,
    INTERNAL_SERVER_ERROR
}
```
```java