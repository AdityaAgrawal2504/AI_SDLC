package com.chatapp.chatservice.repository;

import com.chatapp.chatservice.domain.Message_C1A7;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MessageRepository_C1A7 {
    private final ConcurrentHashMap<String, Message_C1A7> messages = new ConcurrentHashMap<>();

    /**
     * Saves a message to the in-memory store.
     */
    public Message_C1A7 save(Message_C1A7 message) {
        messages.put(message.getMessageId(), message);
        return message;
    }

    /**
     * Finds a message by its ID.
     */
    public Optional<Message_C1A7> findById(String messageId) {
        return Optional.ofNullable(messages.get(messageId));
    }
}
```
```java
// src/main/java/com/chatapp/chatservice/repository/UserRepository_C1A7.java