package com.example.auth.exception;

import org.springframework.http.HttpStatus;

/**
 * Exception thrown for invalid user credentials (phone number or password).
 */
public class InvalidCredentialsException extends ApplicationException {
    public InvalidCredentialsException(String message) {
        super(HttpStatus.UNAUTHORIZED, ErrorCode.INVALID_CREDENTIALS, message);
    }
}
```
src/main/java/com/example/auth/exception/AccountLockedException.java
```java