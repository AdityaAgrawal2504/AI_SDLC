package com.example.fetchconversationsapi1.dto;

import lombok.Data;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

/**
 * DTO for the summary of a single conversation.
 */
@Data
public class ConversationSummaryDto_FCAPI_1 {
    private UUID id;
    private String title;
    private List<ParticipantDto_FCAPI_1> participants;
    private MessageSnippetDto_FCAPI_1 lastMessage;
    private int unreadCount;
    private boolean isSeen;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}
```
```java
// src/main/java/com/example/fetchconversationsapi1/dto/PaginatedConversationsResponse_FCAPI_1.java