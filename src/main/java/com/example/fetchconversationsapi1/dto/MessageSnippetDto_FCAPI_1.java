package com.example.fetchconversationsapi1.dto;

import lombok.Data;

import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * DTO for a brief summary of the last message in a conversation.
 */
@Data
public class MessageSnippetDto_FCAPI_1 {
    private UUID id;
    private UUID senderId;
    private String text;
    private OffsetDateTime timestamp;
}
```
```java
// src/main/java/com/example/fetchconversationsapi1/dto/ParticipantDto_FCAPI_1.java