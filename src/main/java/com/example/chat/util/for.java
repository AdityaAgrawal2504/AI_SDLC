package com.example.chat.util;

import com.example.chat.exception.ChatStreamExceptionGRPC;
import com.example.chat.grpc.spec.JoinRequestGRPC;
import com.example.chat.grpc.spec.SendMessageGRPC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import static com.example.chat.enums.StreamErrorCodeGRPC.INVALID_ARGUMENT;

import java.util.UUID;

/**
 * Utility class for validating incoming gRPC requests against business rules.
 */
@Component
public class RequestValidatorGRPC {

    private final int maxMessageLength;

    public RequestValidatorGRPC(@Value("${chat.message.max-length}") int maxMessageLength) {
        this.maxMessageLength = maxMessageLength;
    }

    /**
     * Validates a JoinRequest, throwing an exception on failure.
     * @param request The JoinRequest to validate.
     */
    public void validateJoinRequest(JoinRequestGRPC request) {
        if (!StringUtils.hasText(request.getUserId()) ||
            !StringUtils.hasText(request.getChannelId()) ||
            !StringUtils.hasText(request.getSessionToken())) {
            throw new ChatStreamExceptionGRPC(INVALID_ARGUMENT, "user_id, channel_id, and session_token must not be empty.");
        }
    }

    /**
     * Validates a SendMessage request, throwing an exception on failure.
     * @param request The SendMessage to validate.
     */
    public void validateSendMessage(SendMessageGRPC request) {
        if (!StringUtils.hasText(request.getContent()) || request.getContent().length() > maxMessageLength) {
            throw new ChatStreamExceptionGRPC(INVALID_ARGUMENT,
                "Message content must not be empty and must be less than " + maxMessageLength + " characters.");
        }
        try {
            UUID.fromString(request.getClientMessageId());
        } catch (IllegalArgumentException e) {
            throw new ChatStreamExceptionGRPC(INVALID_ARGUMENT, "client_message_id must be a valid UUID.");
        }
    }
}
```
src/main/java/com/example/chat/util/StreamResponseFactoryGRPC.java
```java