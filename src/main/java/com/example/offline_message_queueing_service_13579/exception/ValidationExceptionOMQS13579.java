package com.example.offline_message_queueing_service_13579.exception;

import com.example.offline_message_queueing_service_13579.constants.ErrorCodeOMQS13579;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ValidationExceptionOMQS13579 extends RuntimeException {
    private final ErrorCodeOMQS13579 errorCode;
    private final HttpStatus status;

    public ValidationExceptionOMQS13579(ErrorCodeOMQS13579 errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
        this.status = HttpStatus.BAD_REQUEST;
    }
}
```
```java
// src/main/java/com/example/offline_message_queueing_service_13579/exception/ResourceNotFoundExceptionOMQS13579.java