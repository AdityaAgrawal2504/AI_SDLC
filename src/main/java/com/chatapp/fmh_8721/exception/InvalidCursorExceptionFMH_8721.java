package com.chatapp.fmh_8721.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception for 400 Bad Request errors related to an invalid pagination cursor.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidCursorExceptionFMH_8721 extends RuntimeException {
    public InvalidCursorExceptionFMH_8721(String message) {
        super(message);
    }
}
```
```java
// src/main/java/com/chatapp/fmh_8721/exception/ResourceNotFoundExceptionFMH_8721.java