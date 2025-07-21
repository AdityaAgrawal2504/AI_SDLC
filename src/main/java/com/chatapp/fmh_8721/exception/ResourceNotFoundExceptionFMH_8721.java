package com.chatapp.fmh_8721.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception for 404 Not Found errors.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundExceptionFMH_8721 extends RuntimeException {
    public ResourceNotFoundExceptionFMH_8721(String message) {
        super(message);
    }
}
```
```java
// src/main/java/com/chatapp/fmh_8721/exception/GlobalExceptionHandlerFMH_8721.java