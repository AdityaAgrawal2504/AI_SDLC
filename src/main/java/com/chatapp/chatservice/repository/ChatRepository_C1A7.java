package com.chatapp.chatservice.repository;

import com.chatapp.chatservice.domain.Chat_C1A7;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class ChatRepository_C1A7 {

    private final ConcurrentHashMap<String, Chat_C1A7> chats = new ConcurrentHashMap<>();

    /**
     * Pre-populates in-memory data with sample chats for demonstration.
     */
    @PostConstruct
    private void setup() {
        // Chat between user1 and user2
        chats.put("chat-12", new Chat_C1A7("chat-12", Set.of("user-1", "user-2"), Set.of()));
        // Group chat with user1, user3, user4. user1 is muted.
        chats.put("chat-134", new Chat_C1A7("chat-134", Set.of("user-1", "user-3", "user-4"), Set.of("user-1")));
    }

    /**
     * Finds a chat by its ID.
     */
    public Optional<Chat_C1A7> findById(String chatId) {
        return Optional.ofNullable(chats.get(chatId));
    }
}
```
```java
// src/main/java/com/chatapp/chatservice/repository/MessageRepository_C1A7.java