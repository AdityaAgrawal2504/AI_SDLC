package com.chatapp.chatservice.service;

import com.chatapp.chatservice.domain.Chat_C1A7;
import com.chatapp.chatservice.domain.User_C1A7;
import com.chatapp.chatservice.repository.ChatRepository_C1A7;
import com.chatapp.chatservice.repository.UserRepository_C1A7;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatPermissionService_C1A7 {

    private final ChatRepository_C1A7 chatRepository;
    private final UserRepository_C1A7 userRepository;

    /**
     * Verifies if a user is a member of a given chat.
     */
    public Chat_C1A7 findAndVerifyChatMembership(String chatId, String userId) {
        Chat_C1A7 chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new StatusRuntimeException(Status.NOT_FOUND.withDescription("Chat not found: " + chatId)));

        if (!chat.getParticipantIds().contains(userId)) {
            throw new StatusRuntimeException(Status.PERMISSION_DENIED.withDescription("User " + userId + " is not a member of chat " + chatId));
        }
        return chat;
    }

    /**
     * Checks if a sender is muted in a specific chat.
     */
    public boolean isSenderMuted(Chat_C1A7 chat, String senderId) {
        return chat.getMutedUserIds().contains(senderId);
    }

    /**
     * Filters a set of recipient IDs to exclude any who have blocked the sender.
     */
    public Set<String> filterBlockedRecipients(Set<String> recipientIds, String senderId) {
        return recipientIds.stream()
                .filter(recipientId -> {
                    User_C1A7 recipient = userRepository.findById(recipientId).orElse(null);
                    return recipient != null && (recipient.getBlockedUserIds() == null || !recipient.getBlockedUserIds().contains(senderId));
                })
                .collect(Collectors.toSet());
    }
}
```
```java
// src/main/java/com/chatapp/chatservice/service/ConnectionManager_C1A7.java