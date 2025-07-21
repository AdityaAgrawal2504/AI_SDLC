package com.example.conversations.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

/**
 * DTO representing a single message conversation.
 */
@Data
@Builder
public class ConversationDtoGCAPI_9876 {
    private UUID id;
    private String title;
    private List<ParticipantDtoGCAPI_9876> participants;
    private MessageDtoGCAPI_9876 lastMessage;
    private int unreadCount;
    @JsonProperty("isRead")
    private boolean isRead;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}
```
```java
// src/main/java/com/example/conversations/api/dto/MessageDtoGCAPI_9876.java