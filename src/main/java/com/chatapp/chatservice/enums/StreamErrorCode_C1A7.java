package com.chatapp.chatservice.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum StreamErrorCode_C1A7 {
    MESSAGE_SEND_FAILED_BLOCKED("Message could not be sent because the recipient has blocked the sender."),
    MESSAGE_SEND_FAILED_MUTED("Message could not be sent because the sender is muted in the chat."),
    INVALID_STATUS_UPDATE("The requested status transition is not allowed (e.g., trying to mark a message as SEEN that you sent)."),
    MALFORMED_REQUEST("The client sent a message that failed validation (e.g., missing a required field)."),
    RATE_LIMIT_EXCEEDED("Client has exceeded the message rate limit."),
    NOT_FOUND("The specified resource (e.g., messageId) was not found."),
    UNKNOWN_ERROR("An unknown server error occurred.");

    private final String description;
}
```
```java
// src/main/java/com/chatapp/chatservice/exception/StreamErrorException_C1A7.java