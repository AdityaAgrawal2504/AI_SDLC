package com.example.chat.service;

import com.example.chat.grpc.spec.ChatStreamResponseGRPC;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

/**
 * Represents a single chat channel, managing its participants and broadcasting messages.
 */
@Slf4j
public class ChatChannelGRPC {

    private final String channelId;
    private final ConcurrentHashMap<String, StreamObserver<ChatStreamResponseGRPC>> participants = new ConcurrentHashMap<>();

    public ChatChannelGRPC(String channelId) {
        this.channelId = channelId;
    }

    /**
     * Adds a new participant to the channel.
     * @param userId The ID of the user joining.
     * @param responseObserver The gRPC stream observer for the user's connection.
     */
    public void join(String userId, StreamObserver<ChatStreamResponseGRPC> responseObserver) {
        log.info("User '{}' joining channel '{}'", userId, channelId);
        participants.put(userId, responseObserver);
    }

    /**
     * Removes a participant from the channel.
     * @param userId The ID of the user leaving.
     */
    public void leave(String userId) {
        log.info("User '{}' leaving channel '{}'", userId, channelId);
        participants.remove(userId);
    }

    /**
     * Broadcasts a response to all participants in the channel.
     * @param response The response to broadcast.
     */
    public void broadcast(ChatStreamResponseGRPC response) {
        broadcast(response, null);
    }

    /**
     * Broadcasts a response to all participants except one.
     * @param response The response to broadcast.
     * @param excludedUserId The ID of the user to exclude from the broadcast.
     */
    public void broadcast(ChatStreamResponseGRPC response, String excludedUserId) {
        String messageType = response.getResponseOneofCase().name();
        log.debug("Broadcasting message of type '{}' to channel '{}', excluding user '{}'", messageType, channelId, excludedUserId);
        
        participants.forEach((userId, observer) -> {
            if (!userId.equals(excludedUserId)) {
                sendToObserver(observer, response, userId);
            }
        });
    }

    /**
     * Safely sends a message to a stream observer, handling potential exceptions.
     */
    private void sendToObserver(StreamObserver<ChatStreamResponseGRPC> observer, ChatStreamResponseGRPC response, String userId) {
        try {
            observer.onNext(response);
        } catch (Exception e) {
            log.warn("Failed to send message to user {}: {}. Removing from channel.", userId, e.getMessage());
            leave(userId);
        }
    }
}
```
src/main/java/com/example/chat/service/ChatChannelManagerGRPC.java
<ctrl60><ctrl62><ctrl61>```java