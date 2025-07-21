package com.chatapp.fmh_8721.exception;

/**
 * Server-defined error codes for use in ApiError responses.
 */
public enum ErrorCodeFMH_8721 {
    INVALID_PATH_PARAMETER,
    INVALID_QUERY_PARAMETER,
    INVALID_CURSOR,
    UNAUTHENTICATED,
    ACCESS_DENIED,
    CONVERSATION_NOT_FOUND,
    INTERNAL_SERVER_ERROR
}
```
```java
// src/main/java/com/chatapp/fmh_8721/exception/ForbiddenExceptionFMH_8721.java