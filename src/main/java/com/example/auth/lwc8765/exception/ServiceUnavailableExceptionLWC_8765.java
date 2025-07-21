package com.example.auth.lwc8765.exception;

import com.example.auth.lwc8765.util.ErrorCodeLWC_8765;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ServiceUnavailableExceptionLWC_8765 extends RuntimeException {
    private final ErrorCodeLWC_8765 errorCode;

    public ServiceUnavailableExceptionLWC_8765(ErrorCodeLWC_8765 errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
}
```
```java
// src/main/java/com/example/auth/lwc8765/exception/GlobalExceptionHandlerLWC_8765.java