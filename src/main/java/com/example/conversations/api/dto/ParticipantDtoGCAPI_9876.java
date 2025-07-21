package com.example.conversations.api.dto;

import lombok.Builder;
import lombok.Data;
import java.util.UUID;

/**
 * DTO representing a user participating in a conversation.
 */
@Data
@Builder
public class ParticipantDtoGCAPI_9876 {
    private UUID userId;
    private String displayName;
    private String avatarUrl;
}
```
```java
// src/main/java/com/example/conversations/api/exception/CustomApiExceptionGCAPI_9876.java