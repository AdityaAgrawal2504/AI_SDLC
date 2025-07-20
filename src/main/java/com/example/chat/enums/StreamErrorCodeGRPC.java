package com.example.chat.enums;

import lombok.Getter;

/**
 * Defines application-specific error codes for StreamError messages.
 */
@Getter
public enum StreamErrorCodeGRPC {
    // According to https://grpc.github.io/grpc/core/md_doc_statuscodes.html
    INVALID_ARGUMENT(3, "Client specified an invalid argument."),
    UNAUTHENTICATED(16, "Request not authenticated."),
    PERMISSION_DENIED(7, "Client does not have permission to execute the specified operation."),
    NOT_FOUND(5, "Some requested entity (e.g., file or directory) was not found."),
    RESOURCE_EXHAUSTED(8, "A resource has been exhausted, perhaps a per-user quota, or the entire file system is out of space."),
    INTERNAL_ERROR(13, "Internal server error."),
    MESSAGE_VALIDATION_FAILED(3, "Message content failed validation (e.g., length).");

    private final int code;
    private final String description;

    StreamErrorCodeGRPC(int code, String description) {
        this.code = code;
        this.description = description;
    }
}
```
src/main/java/com/example/chat/exception/ChatStreamExceptionGRPC.java
```java