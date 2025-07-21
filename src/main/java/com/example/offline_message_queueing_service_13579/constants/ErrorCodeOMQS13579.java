package com.example.offline_message_queueing_service_13579.constants;

public enum ErrorCodeOMQS13579 {
    VALIDATION_ERROR,
    PERSISTENCE_FAILURE,
    DELETION_FAILURE,
    RESOURCE_NOT_FOUND,
    SERVICE_UNAVAILABLE,
    INVALID_REQUEST_BODY,
    USER_ID_MISMATCH
}
```
```java
// src/main/java/com/example/offline_message_queueing_service_13579/converter/PayloadJsonConverterOMQS13579.java