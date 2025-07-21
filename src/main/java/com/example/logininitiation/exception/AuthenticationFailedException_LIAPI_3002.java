package com.example.logininitiation.exception;

import com.example.logininitiation.enums.ErrorCode_LIAPI_2001;
import org.springframework.http.HttpStatus;

public class AuthenticationFailedException_LIAPI_3002 extends ApiException_LIAPI_3001 {
    private static final String DEFAULT_MESSAGE = "The credentials provided are invalid.";

    public AuthenticationFailedException_LIAPI_3002() {
        super(HttpStatus.UNAUTHORIZED, ErrorCode_LIAPI_2001.AUTHENTICATION_FAILED, DEFAULT_MESSAGE);
    }
}
```
```java
// src/main/java/com/example/logininitiation/exception/GlobalExceptionHandler_LIAPI_4001.java