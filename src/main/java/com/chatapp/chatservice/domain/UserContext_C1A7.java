package com.chatapp.chatservice.domain;

import io.grpc.Context;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class UserContext_C1A7 {
    public static final Context.Key<UserContext_C1A7> KEY = Context.key("userContext_C1A7");
    private final String userId;
}
```
```java
// src/main/java/com/chatapp/chatservice/enums/StreamErrorCode_C1A7.java