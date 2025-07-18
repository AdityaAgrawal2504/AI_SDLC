package com.example.auth.exception;

import org.springframework.http.HttpStatus;

/**
 * Exception thrown when a requested resource (e.g., a User) is not found.
 */
public class ResourceNotFoundException extends ApplicationException {
    public ResourceNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, ErrorCode.RESOURCE_NOT_FOUND, message);
    }
}
```
src/main/java/com/example/auth/exception/GlobalExceptionHandler.java
```java