package com.example.chat.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents the state of a connected user within a chat channel.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSessionGRPC {
    private String userId;
    private String displayName;
    private String channelId;
}
```
src/main/java/com/example/chat/service/AuthServiceGRPC.java
```java