package com.example.auth.exception;

import com.example.auth.util.ErrorCodeAUTH;
import lombok.Getter;

/**
 * Custom runtime exception for handling application-specific errors.
 */
@Getter
public class AuthServiceExceptionAUTH extends RuntimeException {

    private final ErrorCodeAUTH errorCode;

    public AuthServiceExceptionAUTH(ErrorCodeAUTH errorCode) {
        super(errorCode.getDefaultMessage());
        this.errorCode = errorCode;
    }

    public AuthServiceExceptionAUTH(ErrorCodeAUTH errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public AuthServiceExceptionAUTH(ErrorCodeAUTH errorCode, Throwable cause) {
        super(errorCode.getDefaultMessage(), cause);
        this.errorCode = errorCode;
    }
}
```
```java
// src/main/java/com/example/auth/exception/GlobalExceptionHandlerAUTH.java