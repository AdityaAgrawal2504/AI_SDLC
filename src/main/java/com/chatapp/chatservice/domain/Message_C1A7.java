package com.chatapp.chatservice.domain;

import com.chatapp.chatservice.grpc.MessageStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Message_C1A7 {
    private String messageId;
    private String clientMessageId;
    private String chatId;
    private String senderId;
    private String content;
    private MessageStatus status;
    private Instant createdAt;
    private Instant updatedAt;
}
```
```java
// src/main/java/com/chatapp/chatservice/domain/User_C1A7.java