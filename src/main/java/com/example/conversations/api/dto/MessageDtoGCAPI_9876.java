package com.example.conversations.api.dto;

import com.example.conversations.api.model.MessageContentTypeGCAPI_9876;
import lombok.Builder;
import lombok.Data;
import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * DTO representing a single message, often used as a preview snippet.
 */
@Data
@Builder
public class MessageDtoGCAPI_9876 {
    private UUID id;
    private UUID senderId;
    private String content;
    private MessageContentTypeGCAPI_9876 contentType;
    private OffsetDateTime createdAt;
}
```
```java
// src/main/java/com/example/conversations/api/dto/PaginatedConversationsResponseGCAPI_9876.java