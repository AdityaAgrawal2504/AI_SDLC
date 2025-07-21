package com.chatapp.chatservice.util;

import com.chatapp.chatservice.enums.StreamErrorCode_C1A7;
import com.chatapp.chatservice.exception.StreamErrorException_C1A7;
import com.chatapp.chatservice.grpc.SendMessageRequest;
import com.chatapp.chatservice.grpc.UpdateStatusRequest;

public final class RequestValidator_C1A7 {

    private static final int MAX_CONTENT_LENGTH = 10000;

    /**
     * Validates a SendMessageRequest.
     */
    public static void validate(SendMessageRequest request) {
        if (isNullOrEmpty(request.getChatId())) {
            throw new StreamErrorException_C1A7(StreamErrorCode_C1A7.MALFORMED_REQUEST, "chatId is required.", request.getClientMessageId());
        }
        if (isNullOrEmpty(request.getClientMessageId())) {
            throw new StreamErrorException_C1A7(StreamErrorCode_C1A7.MALFORMED_REQUEST, "clientMessageId is required.", request.getClientMessageId());
        }
        if (isNullOrEmpty(request.getContent())) {
            throw new StreamErrorException_C1A7(StreamErrorCode_C1A7.MALFORMED_REQUEST, "content cannot be empty.", request.getClientMessageId());
        }
        if (request.getContent().length() > MAX_CONTENT_LENGTH) {
            throw new StreamErrorException_C1A7(StreamErrorCode_C1A7.MALFORMED_REQUEST, "content exceeds max length of " + MAX_CONTENT_LENGTH, request.getClientMessageId());
        }
    }

    /**
     * Validates an UpdateStatusRequest.
     */
    public static void validate(UpdateStatusRequest request) {
        if (isNullOrEmpty(request.getChatId())) {
            throw new StreamErrorException_C1A7(StreamErrorCode_C1A7.MALFORMED_REQUEST, "chatId is required.", null);
        }
        if (isNullOrEmpty(request.getMessageId())) {
            throw new StreamErrorException_C1A7(StreamErrorCode_C1A7.MALFORMED_REQUEST, "messageId is required.", null);
        }
        if (request.getStatus() == com.chatapp.chatservice.grpc.MessageStatus.STATUS_UNSPECIFIED) {
            throw new StreamErrorException_C1A7(StreamErrorCode_C1A7.MALFORMED_REQUEST, "A valid status is required.", null);
        }
    }

    private static boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }
}
```
```java
// src/main/java/com/chatapp/chatservice/util/TimestampConverter_C1A7.java