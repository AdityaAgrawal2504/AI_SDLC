package com.example.fetchconversationsapi1.util;

import com.example.fetchconversationsapi1.dto.ConversationSummaryDto_FCAPI_1;
import com.example.fetchconversationsapi1.dto.MessageSnippetDto_FCAPI_1;
import com.example.fetchconversationsapi1.dto.ParticipantDto_FCAPI_1;
import com.example.fetchconversationsapi1.model.ConversationEntity_FCAPI_1;
import com.example.fetchconversationsapi1.model.ConversationParticipantEntity_FCAPI_1;
import com.example.fetchconversationsapi1.model.MessageEntity_FCAPI_1;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Utility class for mapping JPA entities to DTOs.
 */
public class DtoMapper_FCAPI_1 {

    /**
     * Maps a ConversationEntity to a ConversationSummaryDto.
     */
    public static ConversationSummaryDto_FCAPI_1 toConversationSummaryDto(ConversationEntity_FCAPI_1 entity, UUID currentUserId) {
        if (entity == null) {
            return null;
        }
        ConversationSummaryDto_FCAPI_1 dto = new ConversationSummaryDto_FCAPI_1();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());

        dto.setParticipants(mapParticipants(entity.getParticipants()));
        dto.setLastMessage(mapLastMessage(entity.getLastMessage()));
        
        // Calculate seen status and unread count for the current user
        boolean isSeen = false;
        int unreadCount = 0; // Placeholder for unread count logic

        ConversationParticipantEntity_FCAPI_1 currentUserParticipant = entity.getParticipants().stream()
                .filter(p -> p.getUser().getId().equals(currentUserId))
                .findFirst()
                .orElse(null);

        if (currentUserParticipant != null && entity.getLastMessage() != null) {
            isSeen = entity.getLastMessage().getId().equals(currentUserParticipant.getLastSeenMessageId());
        } else if (entity.getLastMessage() == null) {
            isSeen = true; // No messages, so it's "seen"
        }
        
        dto.setSeen(isSeen);
        dto.setUnreadCount(unreadCount); // Complex calculation, setting to 0 for this example

        return dto;
    }

    private static List<ParticipantDto_FCAPI_1> mapParticipants(List<ConversationParticipantEntity_FCAPI_1> participants) {
        if (participants == null || participants.isEmpty()) {
            return Collections.emptyList();
        }
        return participants.stream().map(p -> {
            ParticipantDto_FCAPI_1 dto = new ParticipantDto_FCAPI_1();
            dto.setUserId(p.getUser().getId());
            dto.setDisplayName(p.getUser().getDisplayName());
            dto.setAvatarUrl(p.getUser().getAvatarUrl());
            return dto;
        }).collect(Collectors.toList());
    }

    private static MessageSnippetDto_FCAPI_1 mapLastMessage(MessageEntity_FCAPI_1 message) {
        if (message == null) {
            return null;
        }
        MessageSnippetDto_FCAPI_1 dto = new MessageSnippetDto_FCAPI_1();
        dto.setId(message.getId());
        dto.setSenderId(message.getSender().getId());
        dto.setTimestamp(message.getCreatedAt());

        // Create a snippet
        String content = message.getContent();
        dto.setText(content.length() > 100 ? content.substring(0, 100) + "..." : content);

        return dto;
    }
}
```
```java
// src/main/java/com/example/fetchconversationsapi1/security/AuthenticationFacade_FCAPI_1.java