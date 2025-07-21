package com.example.logininitiation.exception;

import com.example.logininitiation.enums.ErrorCode_LIAPI_2001;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiException_LIAPI_3001 extends RuntimeException {
    private final HttpStatus status;
    private final ErrorCode_LIAPI_2001 errorCode;

    public ApiException_LIAPI_3001(HttpStatus status, ErrorCode_LIAPI_2001 errorCode, String message) {
        super(message);
        this.status = status;
        this.errorCode = errorCode;
    }
}
```
```java
// src/main/java/com/example/logininitiation/exception/AuthenticationFailedException_LIAPI_3002.java