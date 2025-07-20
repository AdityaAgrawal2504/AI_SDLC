package com.example.chat.exception;

import com.example.chat.enums.StreamErrorCodeGRPC;
import lombok.Getter;

/**
 * Custom exception for handling specific, recoverable errors within the chat stream.
 */
@Getter
public class ChatStreamExceptionGRPC extends RuntimeException {

    private final StreamErrorCodeGRPC errorCode;
    private final String details;

    public ChatStreamExceptionGRPC(StreamErrorCodeGRPC errorCode, String details) {
        super(String.format("[%s] %s", errorCode.name(), details));
        this.errorCode = errorCode;
        this.details = details;
    }
}
```
src/main/java/com/example/chat/logging/ExternalLogSystemAppenderGRPC.java
```java