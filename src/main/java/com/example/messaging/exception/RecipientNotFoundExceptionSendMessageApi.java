package com.example.messaging.exception;

import com.example.messaging.enums.ErrorCodeSendMessageApi;
import org.springframework.http.HttpStatus;

/**
 * Exception thrown when a specified recipient cannot be found.
 */
public class RecipientNotFoundExceptionSendMessageApi extends ApiExceptionSendMessageApi {
    public RecipientNotFoundExceptionSendMessageApi(String message) {
        super(HttpStatus.NOT_FOUND, ErrorCodeSendMessageApi.RECIPIENT_NOT_FOUND, message, null);
    }
}
```
```java