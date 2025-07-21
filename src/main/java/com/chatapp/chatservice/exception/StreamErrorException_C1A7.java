package com.chatapp.chatservice.exception;

import com.chatapp.chatservice.enums.StreamErrorCode_C1A7;
import lombok.Getter;

@Getter
public class StreamErrorException_C1A7 extends RuntimeException {
    private final StreamErrorCode_C1A7 errorCode;
    private final String originalClientMessageId;

    public StreamErrorException_C1A7(StreamErrorCode_C1A7 errorCode, String message, String originalClientMessageId) {
        super(message);
        this.errorCode = errorCode;
        this.originalClientMessageId = originalClientMessageId;
    }
}
```
```java
// src/main/java/com/chatapp/chatservice/grpc/AuthInterceptor_C1A7.java