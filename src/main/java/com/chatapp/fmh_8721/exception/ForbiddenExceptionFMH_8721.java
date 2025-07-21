package com.chatapp.fmh_8721.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception for 403 Forbidden errors.
 */
@ResponseStatus(HttpStatus.FORBIDDEN)
public class ForbiddenExceptionFMH_8721 extends RuntimeException {
    public ForbiddenExceptionFMH_8721(String message) {
        super(message);
    }
}
```
```java
// src/main/java/com/chatapp/fmh_8721/exception/InvalidCursorExceptionFMH_8721.java