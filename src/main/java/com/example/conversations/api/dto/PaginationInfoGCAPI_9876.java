package com.example.conversations.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

/**
 * DTO containing metadata for cursor-based pagination.
 */
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaginationInfoGCAPI_9876 {
    private long totalCount;
    private int limit;
    private String nextCursor;
    private boolean hasMore;
}
```
```java
// src/main/java/com/example/conversations/api/dto/ParticipantDtoGCAPI_9876.java