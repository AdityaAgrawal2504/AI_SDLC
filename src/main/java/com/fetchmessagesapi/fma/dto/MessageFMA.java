package com.fetchmessagesapi.fma.dto;

import com.fetchmessagesapi.fma.enums.MessageStatusFMA;
import lombok.Builder;
import lombok.Getter;
import java.time.Instant;
import java.util.UUID;

/**
 * DTO representing a single message in the API response.
 */
@Getter
@Builder
public class MessageFMA {
    private final UUID id;
    private final UUID conversationId;
    private final SenderFMA sender;
    private final String subject;
    private final String bodySnippet;
    private final Instant timestamp;
    private final MessageStatusFMA status;
}
```
```java
// src/main/java/com/fetchmessagesapi/fma/dto/MessageListResponseFMA.java