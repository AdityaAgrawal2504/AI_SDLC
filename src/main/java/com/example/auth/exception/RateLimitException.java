package com.example.auth.exception;

import org.springframework.http.HttpStatus;

/**
 * Exception thrown when a user exceeds the allowed rate limit for an action.
 */
public class RateLimitException extends ApplicationException {
    public RateLimitException(String message) {
        super(HttpStatus.TOO_MANY_REQUESTS, ErrorCode.RATE_LIMIT_EXCEEDED, message);
    }
}
```
src/main/java/com/example/auth/exception/ResourceNotFoundException.java
```java