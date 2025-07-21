package com.chatapp.chatservice.service;

import com.chatapp.chatservice.grpc.ServerToClientMessage;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Log4j2
public class ConnectionManager_C1A7 {

    // Map<UserId, Map<SessionId, StreamObserver>>
    private final ConcurrentHashMap<String, Map<String, StreamObserver<ServerToClientMessage>>> activeConnections = new ConcurrentHashMap<>();

    /**
     * Registers a new client connection stream.
     */
    public void register(String userId, String sessionId, StreamObserver<ServerToClientMessage> responseObserver) {
        activeConnections.computeIfAbsent(userId, k -> new ConcurrentHashMap<>()).put(sessionId, responseObserver);
        log.info("Registered connection for userId={}, sessionId={}. Total sessions for user: {}", userId, sessionId, activeConnections.get(userId).size());
    }

    /**
     * Removes a client connection stream.
     */
    public void unregister(String userId, String sessionId) {
        Map<String, StreamObserver<ServerToClientMessage>> userSessions = activeConnections.get(userId);
        if (userSessions != null) {
            userSessions.remove(sessionId);
            if (userSessions.isEmpty()) {
                activeConnections.remove(userId);
            }
            log.info("Unregistered connection for userId={}, sessionId={}", userId, sessionId);
        }
    }

    /**
     * Sends a message to all active connections for a set of user IDs.
     */
    public void broadcast(Set<String> userIds, ServerToClientMessage message) {
        for (String userId : userIds) {
            Map<String, StreamObserver<ServerToClientMessage>> userSessions = activeConnections.get(userId);
            if (userSessions != null) {
                userSessions.values().forEach(observer -> sendMessage(observer, message, userId));
            }
        }
    }

    /**
     * Safely sends a message to a single stream observer, handling potential errors.
     */
    private void sendMessage(StreamObserver<ServerToClientMessage> observer, ServerToClientMessage message, String userId) {
        try {
            observer.onNext(message);
        } catch (Exception e) {
            log.error("Failed to send message to userId {}: {}", userId, e.getMessage());
            // Optionally, we could try to remove the defunct observer here.
            // This could happen if the client connection is broken but onCompleted/onError hasn't been processed yet.
            if (e instanceof IllegalStateException && e.getMessage().contains("CANCELLED")) {
                 log.warn("Stream for user {} was cancelled. Connection will be cleaned up.", userId);
            }
        }
    }
}
```
```java
// src/main/java/com/chatapp/chatservice/service/MessageService_C1A7.java