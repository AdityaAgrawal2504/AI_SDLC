package com.chatapp.chatservice.repository;

import com.chatapp.chatservice.domain.User_C1A7;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class UserRepository_C1A7 {

    private final ConcurrentHashMap<String, User_C1A7> users = new ConcurrentHashMap<>();

    /**
     * Pre-populates in-memory data with sample users for demonstration.
     */
    @PostConstruct
    private void setup() {
        users.put("user-1", new User_C1A7("user-1", "Alice", Set.of()));
        users.put("user-2", new User_C1A7("user-2", "Bob", Set.of("user-3"))); // Bob has blocked user-3
        users.put("user-3", new User_C1A7("user-3", "Charlie", Set.of()));
        users.put("user-4", new User_C1A7("user-4", "David", Set.of()));
    }

    /**
     * Finds a user by their ID.
     */
    public Optional<User_C1A7> findById(String userId) {
        return Optional.ofNullable(users.get(userId));
    }
}
```
```java
// src/main/java/com/chatapp/chatservice/service/AuthService_C1A7.java