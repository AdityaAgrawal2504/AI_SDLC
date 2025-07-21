package com.example.conversations.api.model;

/**
 * Enumeration of machine-readable error codes for the API.
 */
public enum ErrorCodeGCAPI_9876 {
    // 400 Bad Request
    INVALID_PARAMETER_VALUE,
    INVALID_PARAMETER_TYPE,
    PARAMETER_OUT_OF_RANGE,
    INVALID_CURSOR,
    SEARCH_QUERY_TOO_LONG,

    // 401 Unauthorized
    AUTHENTICATION_FAILED,

    // 500 Internal Server Error
    DATABASE_ERROR,
    SEARCH_SERVICE_UNAVAILABLE,
    UNEXPECTED_ERROR
}
```
```java
// src/main/java/com/example/conversations/api/model/MessageContentTypeGCAPI_9876.java