package com.example.chat.service;

import com.example.chat.grpc.spec.ChatMessageGRPC;
import com.example.chat.grpc.spec.SendMessageGRPC;
import com.example.chat.model.UserSessionGRPC;
import com.google.protobuf.Timestamp;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Repository for storing and retrieving chat messages.
 */
public interface MessageRepositoryGRPC {
    /**
     * Saves a new chat message.
     * @param request The message send request.
     * @param session The user session of the sender.
     * @return The saved ChatMessage object.
     */
    ChatMessageGRPC saveMessage(SendMessageGRPC request, UserSessionGRPC session);

    /**
     * Retrieves the recent message history for a channel.
     * @param channelId The ID of the channel.
     * @return A list of recent chat messages.
     */
    List<ChatMessageGRPC> getChannelHistory(String channelId);
}

@Repository
class InMemoryMessageRepositoryGRPCImpl implements MessageRepositoryGRPC {

    private final ConcurrentHashMap<String, List<ChatMessageGRPC>> messagesByChannel = new ConcurrentHashMap<>();

    /**
     * Saves a message to the in-memory store and returns the full ChatMessage object.
     */
    @Override
    public ChatMessageGRPC saveMessage(SendMessageGRPC request, UserSessionGRPC session) {
        Instant now = Instant.now();
        ChatMessageGRPC message = ChatMessageGRPC.newBuilder()
                .setMessageId(UUID.randomUUID().toString())
                .setChannelId(session.getChannelId())
                .setSenderId(session.getUserId())
                .setSenderDisplayName(session.getDisplayName())
                .setContent(request.getContent())
                .setTimestamp(Timestamp.newBuilder().setSeconds(now.getEpochSecond()).setNanos(now.getNano()).build())
                .putAllMetadata(request.getMetadataMap())
                .build();

        messagesByChannel.computeIfAbsent(session.getChannelId(), k -> new CopyOnWriteArrayList<>()).add(message);
        return message;
    }

    /**
     * Retrieves the last 50 messages for a channel from the in-memory store.
     */
    @Override
    public List<ChatMessageGRPC> getChannelHistory(String channelId) {
        List<ChatMessageGRPC> history = messagesByChannel.getOrDefault(channelId, Collections.emptyList());
        // Return a sublist of the last 50 messages to avoid sending huge history
        int start = Math.max(0, history.size() - 50);
        return history.subList(start, history.size());
    }
}
```
src/main/java/com/example/chat/service/UserServiceGRPC.java
```java