package com.example.offline_message_queueing_service_13579.exception;

import com.example.offline_message_queueing_service_13579.constants.ErrorCodeOMQS13579;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class PersistenceExceptionOMQS13579 extends RuntimeException {
    private final ErrorCodeOMQS13579 errorCode;
    private final HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

    public PersistenceExceptionOMQS13579(ErrorCodeOMQS13579 errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }
}
```
```java
// src/main/java/com/example/offline_message_queueing_service_13579/exception/GlobalExceptionHandlerOMQS13579.java