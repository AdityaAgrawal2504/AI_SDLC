package com.chatapp.chatservice.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Chat_C1A7 {
    private String chatId;
    private Set<String> participantIds;
    private Set<String> mutedUserIds;
}
```
```java
// src/main/java/com/chatapp/chatservice/domain/Message_C1A7.java