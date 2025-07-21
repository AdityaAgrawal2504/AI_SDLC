package com.example.conversations.api.exception;

import com.example.conversations.api.model.ErrorCodeGCAPI_9876;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Map;

/**
 * Custom exception for handling controlled API errors with specific error codes and status.
 */
@Getter
public class CustomApiExceptionGCAPI_9876 extends RuntimeException {
    private final HttpStatus status;
    private final ErrorCodeGCAPI_9876 errorCode;
    private final Map<String, String> details;

    public CustomApiExceptionGCAPI_9876(HttpStatus status, ErrorCodeGCAPI_9876 errorCode, String message, Map<String, String> details) {
        super(message);
        this.status = status;
        this.errorCode = errorCode;
        this.details = details;
    }

    public CustomApiExceptionGCAPI_9876(HttpStatus status, ErrorCodeGCAPI_9876 errorCode, String message) {
        this(status, errorCode, message, null);
    }
}
```
```java
// src/main/java/com/example/conversations/api/exception/GlobalExceptionHandlerGCAPI_9876.java