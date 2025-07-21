package com.example.offline_message_queueing_service_13579.exception;

import com.example.offline_message_queueing_service_13579.constants.ErrorCodeOMQS13579;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ResourceNotFoundExceptionOMQS13579 extends RuntimeException {
    private final ErrorCodeOMQS13579 errorCode = ErrorCodeOMQS13579.RESOURCE_NOT_FOUND;
    private final HttpStatus status = HttpStatus.NOT_FOUND;

    public ResourceNotFoundExceptionOMQS13579(String message) {
        super(message);
    }
}
```
```java
// src/main/java/com/example/offline_message_queueing_service_13579/exception/PersistenceExceptionOMQS13579.java