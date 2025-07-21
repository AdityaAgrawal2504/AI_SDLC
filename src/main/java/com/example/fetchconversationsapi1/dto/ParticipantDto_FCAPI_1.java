package com.example.fetchconversationsapi1.dto;

import lombok.Data;

import java.util.UUID;

/**
 * DTO for a participant in a conversation.
 */
@Data
public class ParticipantDto_FCAPI_1 {
    private UUID userId;
    private String displayName;
    private String avatarUrl;
}
```
```java
// src/main/java/com/example/fetchconversationsapi1/dto/PaginationInfoDto_FCAPI_1.java