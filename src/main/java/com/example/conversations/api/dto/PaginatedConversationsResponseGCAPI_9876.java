package com.example.conversations.api.dto;

import lombok.Builder;
import lombok.Data;
import java.util.List;

/**
 * DTO for the top-level paginated conversations response.
 */
@Data
@Builder
public class PaginatedConversationsResponseGCAPI_9876 {
    private List<ConversationDtoGCAPI_9876> data;
    private PaginationInfoGCAPI_9876 pagination;
}
```
```java
// src/main/java/com/example/conversations/api/dto/PaginationInfoGCAPI_9876.java