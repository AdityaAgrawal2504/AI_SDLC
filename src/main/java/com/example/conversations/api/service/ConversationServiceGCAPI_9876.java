package com.example.conversations.api.service;

import com.example.conversations.api.dto.PaginatedConversationsResponseGCAPI_9876;
import com.example.conversations.api.model.ConversationSortByGCAPI_9876;
import com.example.conversations.api.model.SortOrderGCAPI_9876;
import java.util.UUID;

/**
 * Interface defining the business logic for managing conversations.
 */
public interface ConversationServiceGCAPI_9876 {
    /**
     * Fetches conversations based on specified criteria.
     *
     * @param userId The ID of the authenticated user.
     * @param search The search term.
     * @param sortBy The field to sort by.
     * @param sortOrder The order of sorting.
     * @param limit The number of items to return.
     * @param cursor The pagination cursor.
     * @return A paginated list of conversations.
     */
    PaginatedConversationsResponseGCAPI_9876 getConversations(
        UUID userId,
        String search,
        ConversationSortByGCAPI_9876 sortBy,
        SortOrderGCAPI_9876 sortOrder,
        int limit,
        String cursor
    );
}
```
```java
// src/main/java/com/example/conversations/api/service/ConversationServiceImplGCAPI_9876.java