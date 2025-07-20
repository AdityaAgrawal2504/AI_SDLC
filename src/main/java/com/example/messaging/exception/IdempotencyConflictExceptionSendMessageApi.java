package com.example.messaging.exception;

import com.example.messaging.enums.ErrorCodeSendMessageApi;
import org.springframework.http.HttpStatus;

/**
 * Exception thrown when an Idempotency-Key is reused with a different request payload.
 */
public class IdempotencyConflictExceptionSendMessageApi extends ApiExceptionSendMessageApi {
    public IdempotencyConflictExceptionSendMessageApi(String message) {
        super(HttpStatus.CONFLICT, ErrorCodeSendMessageApi.IDEMPOTENCY_CONFLICT, message, null);
    }
}
```
```java