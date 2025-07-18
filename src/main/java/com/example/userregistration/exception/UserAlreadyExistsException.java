package com.example.userregistration.exception;

import com.example.userregistration.enums.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Custom exception thrown when a registration attempt is made for an existing user.
 */
@Getter
@ResponseStatus(HttpStatus.CONFLICT)
public class UserAlreadyExistsException extends RuntimeException {
    private final ErrorCode errorCode;

    public UserAlreadyExistsException(String message) {
        super(message);
        this.errorCode = ErrorCode.USER_ALREADY_EXISTS;
    }
}
```
```java
// src/main/java/com/example/userregistration/exception/GlobalExceptionHandler.java