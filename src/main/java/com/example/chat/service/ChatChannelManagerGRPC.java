package com.example.chat.service;

import com.example.chat.grpc.spec.ChatMessageGRPC;
import com.example.chat.grpc.spec.ChatStreamResponseGRPC;
import com.example.chat.grpc.spec.TypingNotificationGRPC;
import com.example.chat.grpc.spec.UserEventGRPC;
import com.example.chat.model.UserSessionGRPC;
import com.google.protobuf.Timestamp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Manages all active chat channels and user subscriptions.
 */
@Service
@Slf4j
public class ChatChannelManagerGRPC {

    private final ConcurrentHashMap<String, ChatChannelGRPC> channels = new ConcurrentHashMap<>();

    /**
     * Subscribes a user to a channel, creating the channel if it doesn't exist.
     * @param session The user's session information.
     * @param responseObserver The gRPC stream observer for the user's connection.
     */
    public void subscribe(UserSessionGRPC session, io.grpc.stub.StreamObserver<ChatStreamResponseGRPC> responseObserver) {
        ChatChannelGRPC channel = channels.computeIfAbsent(session.getChannelId(), ChatChannelGRPC::new);
        channel.join(session.getUserId(), responseObserver);
        broadcastUserEvent(session, UserEventGRPC.EventType.JOINED);
    }

    /**
     * Unsubscribes a user from their channel.
     * @param session The user's session information.
     */
    public void unsubscribe(UserSessionGRPC session) {
        if (session == null || session.getChannelId() == null) {
            log.warn("Attempted to unsubscribe with a null session or channelId.");
            return;
        }
        ChatChannelGRPC channel = channels.get(session.getChannelId());
        if (channel != null) {
            channel.leave(session.getUserId());
            broadcastUserEvent(session, UserEventGRPC.EventType.LEFT);
        }
    }

    /**
     * Broadcasts a new chat message to the appropriate channel.
     * @param message The chat message to broadcast.
     */
    public void broadcastMessage(ChatMessageGRPC message) {
        ChatChannelGRPC channel = channels.get(message.getChannelId());
        if (channel != null) {
            ChatStreamResponseGRPC response = ChatStreamResponseGRPC.newBuilder().setNewMessage(message).build();
            channel.broadcast(response);
        }
    }

    /**
     * Broadcasts a typing notification to the appropriate channel.
     * @param session The session of the user who is typing.
     * @param isTyping The typing status.
     */
    public void broadcastTyping(UserSessionGRPC session, boolean isTyping) {
        ChatChannelGRPC channel = channels.get(session.getChannelId());
        if (channel != null) {
            TypingNotificationGRPC notification = TypingNotificationGRPC.newBuilder()
                    .setUserId(session.getUserId())
                    .setUserDisplayName(session.getDisplayName())
                    .setIsTyping(isTyping)
                    .build();
            ChatStreamResponseGRPC response = ChatStreamResponseGRPC.newBuilder().setTypingNotification(notification).build();
            // Exclude the sender from their own typing notification
            channel.broadcast(response, session.getUserId());
        }
    }

    /**
     * Broadcasts a user join/leave event to the appropriate channel.
     * @param session The session of the user who triggered the event.
     * @param eventType The type of event (JOINED or LEFT).
     */
    private void broadcastUserEvent(UserSessionGRPC session, UserEventGRPC.EventType eventType) {
        ChatChannelGRPC channel = channels.get(session.getChannelId());
        if (channel != null) {
            Instant now = Instant.now();
            UserEventGRPC event = UserEventGRPC.newBuilder()
                    .setUserId(session.getUserId())
                    .setUserDisplayName(session.getDisplayName())
                    .setEventType(eventType)
                    .setTimestamp(Timestamp.newBuilder().setSeconds(now.getEpochSecond()).setNanos(now.getNano()).build())
                    .build();
            ChatStreamResponseGRPC response = ChatStreamResponseGRPC.newBuilder().setUserEvent(event).build();
            // Exclude the user who just joined/left from receiving their own event notification.
            channel.broadcast(response, session.getUserId());
        }
    }
}
```
src/main/java/com/example/chat/util/RequestValidatorGRPC.java
<ctrl60><ctrl62><ctrl61>```java