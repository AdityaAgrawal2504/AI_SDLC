package com.example.auth.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Map;

/**
 * Represents a standardized error response payload.
 */
@Data
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponseAUTH {
    private final String status = "error";
    private final String errorCode;
    private final String message;
    private Map<String, String> details;

    public ErrorResponseAUTH(String errorCode, String message, Map<String, String> details) {
        this.errorCode = errorCode;
        this.message = message;
        this.details = details;
    }
}
```
```java
// src/main/java/com/example/auth/entity/UserAUTH.java