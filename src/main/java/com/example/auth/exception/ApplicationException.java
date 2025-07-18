package com.example.auth.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Base runtime exception for the application, containing an HTTP status and error code.
 */
@Getter
public abstract class ApplicationException extends RuntimeException {
    private final HttpStatus status;
    private final ErrorCode errorCode;

    public ApplicationException(HttpStatus status, ErrorCode errorCode, String message) {
        super(message);
        this.status = status;
        this.errorCode = errorCode;
    }
}
```
src/main/java/com/example/auth/exception/InvalidCredentialsException.java
```java