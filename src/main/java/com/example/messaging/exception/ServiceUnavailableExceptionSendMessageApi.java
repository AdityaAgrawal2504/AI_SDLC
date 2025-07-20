package com.example.messaging.exception;

import com.example.messaging.enums.ErrorCodeSendMessageApi;
import org.springframework.http.HttpStatus;

/**
 * Exception thrown when a critical downstream service is unavailable.
 */
public class ServiceUnavailableExceptionSendMessageApi extends ApiExceptionSendMessageApi {
    public ServiceUnavailableExceptionSendMessageApi(String message, Throwable cause) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCodeSendMessageApi.SERVICE_UNAVAILABLE, message, null);
        initCause(cause);
    }
}
```
```java