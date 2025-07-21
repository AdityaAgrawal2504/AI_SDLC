package com.chatapp.chatservice.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User_C1A7 {
    private String userId;
    private String username;
    private Set<String> blockedUserIds;
}
```
```java
// src/main/java/com/chatapp/chatservice/domain/UserContext_C1A7.java