package com.example.messaging.exception;

import com.example.messaging.enums.ErrorCodeSendMessageApi;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Base class for custom application-specific exceptions.
 */
@Getter
public abstract class ApiExceptionSendMessageApi extends RuntimeException {
    private final HttpStatus httpStatus;
    private final ErrorCodeSendMessageApi errorCode;
    private final Object details;

    protected ApiExceptionSendMessageApi(HttpStatus httpStatus, ErrorCodeSendMessageApi errorCode, String message, Object details) {
        super(message);
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
        this.details = details;
    }
}
```
```java