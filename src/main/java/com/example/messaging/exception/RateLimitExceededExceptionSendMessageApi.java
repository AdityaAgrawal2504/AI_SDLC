package com.example.messaging.exception;

import com.example.messaging.enums.ErrorCodeSendMessageApi;
import org.springframework.http.HttpStatus;

/**
 * Exception thrown when a client exceeds the defined rate limits.
 */
public class RateLimitExceededExceptionSendMessageApi extends ApiExceptionSendMessageApi {
    public RateLimitExceededExceptionSendMessageApi(String message) {
        super(HttpStatus.TOO_MANY_REQUESTS, ErrorCodeSendMessageApi.RATE_LIMIT_EXCEEDED, message, null);
    }
}
```
```java