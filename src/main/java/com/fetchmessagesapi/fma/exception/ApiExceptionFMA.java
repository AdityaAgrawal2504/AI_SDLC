package com.fetchmessagesapi.fma.exception;

import com.fetchmessagesapi.fma.enums.ApiErrorFMA;
import lombok.Getter;

/**
 * Custom exception for handling application-specific errors with an associated ApiErrorFMA.
 */
@Getter
public class ApiExceptionFMA extends RuntimeException {
    private final ApiErrorFMA apiError;

    public ApiExceptionFMA(ApiErrorFMA apiError) {
        super(apiError.getMessage());
        this.apiError = apiError;
    }
}
```
```java
// src/main/java/com/fetchmessagesapi/fma/exception/GlobalExceptionHandlerFMA.java