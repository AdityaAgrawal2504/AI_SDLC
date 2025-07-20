package com.example.chat.service;

import org.springframework.stereotype.Service;

/**
 * Service to check user permissions for a channel.
 */
public interface ChannelPermissionServiceGRPC {
    /**
     * Checks if a user has permission to join a specific channel.
     * @param userId The ID of the user.
     * @param channelId The ID of the channel.
     * @return true if the user has permission, false otherwise.
     */
    boolean hasPermission(String userId, String channelId);
}

@Service
class MockChannelPermissionServiceGRPCImpl implements ChannelPermissionServiceGRPC {
    /**
     * Mocks permission check. Denies access if the channel ID is "private-channel"
     * and the user ID is not "admin-user".
     */
    @Override
    public boolean hasPermission(String userId, String channelId) {
        if ("private-channel".equals(channelId)) {
            return "admin-user".equals(userId);
        }
        return true; // Grant access to all other channels by default
    }
}
```
src/main/java/com/example/chat/service/MessageRepositoryGRPC.java
```java