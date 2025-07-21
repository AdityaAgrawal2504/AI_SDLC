package com.example.fetchconversationsapi1.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO for the top-level API response, containing a paginated list of conversations.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaginatedConversationsResponse_FCAPI_1 {
    private List<ConversationSummaryDto_FCAPI_1> data;
    private PaginationInfoDto_FCAPI_1 pagination;
}
```
```java
// src/main/java/com/example/fetchconversationsapi1/model/UserEntity_FCAPI_1.java