package com.example.chat.service;

import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service for retrieving user information.
 */
public interface UserServiceGRPC {
    /**
     * Finds the display name for a given user ID.
     * @param userId The ID of the user.
     * @return An Optional containing the display name, or empty if not found.
     */
    Optional<String> findDisplayNameById(String userId);
}

@Service
class MockUserServiceGRPCImpl implements UserServiceGRPC {
    /**
     * Mocks fetching a user's display name.
     * Returns a capitalized version of the user ID as the display name.
     */
    @Override
    public Optional<String> findDisplayNameById(String userId) {
        if (userId == null || userId.trim().isEmpty()) {
            return Optional.empty();
        }
        // Example: user ID 'john' becomes display name 'John'
        return Optional.of(userId.substring(0, 1).toUpperCase() + userId.substring(1));
    }
}
```
src/main/java/com/example/chat/service/ChatChannelGRPC.java
```java