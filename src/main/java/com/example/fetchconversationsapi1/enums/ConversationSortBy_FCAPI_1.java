package com.example.fetchconversationsapi1.enums;

import lombok.Getter;

/**
 * Enumeration for conversation sorting fields.
 * Maps API-facing names to database entity field names.
 */
@Getter
public enum ConversationSortBy_FCAPI_1 {
    LAST_MESSAGE_TIME("updatedAt"),
    CREATION_TIME("createdAt");

    private final String dbField;

    ConversationSortBy_FCAPI_1(String dbField) {
        this.dbField = dbField;
    }
}
```
```java
// src/main/java/com/example/fetchconversationsapi1/enums/SortOrder_FCAPI_1.java