package com.example.messaging.exception;

import com.example.messaging.enums.ErrorCodeSendMessageApi;
import org.springframework.http.HttpStatus;

/**
 * Exception thrown when authentication fails.
 */
public class UnauthorizedExceptionSendMessageApi extends ApiExceptionSendMessageApi {
    public UnauthorizedExceptionSendMessageApi(String message) {
        super(HttpStatus.UNAUTHORIZED, ErrorCodeSendMessageApi.AUTHENTICATION_FAILURE, message, null);
    }
}
```
```java