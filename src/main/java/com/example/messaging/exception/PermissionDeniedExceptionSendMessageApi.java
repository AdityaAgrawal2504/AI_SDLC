package com.example.messaging.exception;

import com.example.messaging.enums.ErrorCodeSendMessageApi;
import org.springframework.http.HttpStatus;

/**
 * Exception thrown when the authenticated user is not permitted to perform an action.
 */
public class PermissionDeniedExceptionSendMessageApi extends ApiExceptionSendMessageApi {
    public PermissionDeniedExceptionSendMessageApi(String message) {
        super(HttpStatus.FORBIDDEN, ErrorCodeSendMessageApi.PERMISSION_DENIED, message, null);
    }
}
```
```java