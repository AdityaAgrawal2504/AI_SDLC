package com.example.auth.exception;

import lombok.Getter;

/**
 * Custom runtime exception used throughout the application to handle specific business-logic errors.
 */
@Getter
public class ApplicationExceptionAUTH extends RuntimeException {

    private final ErrorCodeAUTH errorCode;

    /**
     * Constructs a new application exception with a specific error code.
     * @param errorCode The ErrorCodeAUTH enum representing the error.
     */
    public ApplicationExceptionAUTH(ErrorCodeAUTH errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    /**
     * Constructs a new application exception with a specific error code and a custom message.
     * @param errorCode The ErrorCodeAUTH enum representing the error.
     * @param message A custom message to override the default.
     */
    public ApplicationExceptionAUTH(ErrorCodeAUTH errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
}
```
```java
// src/main/java/com/example/auth/exception/GlobalExceptionHandlerAUTH.java