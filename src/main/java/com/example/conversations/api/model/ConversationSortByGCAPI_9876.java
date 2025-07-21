package com.example.conversations.api.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Enumeration for the fields available for sorting conversations.
 */
@Getter
@RequiredArgsConstructor
public enum ConversationSortByGCAPI_9876 {
    LAST_MESSAGE_TIME("last_message_time"),
    IS_READ("is_read");

    private final String value;
}
```
```java
// src/main/java/com/example/conversations/api/model/ErrorCodeGCAPI_9876.java