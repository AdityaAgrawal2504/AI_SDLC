package com.example.fetchconversationsapi1.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for pagination metadata.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaginationInfoDto_FCAPI_1 {
    private int currentPage;
    private int pageSize;
    private int totalPages;
    private long totalItems;
}
```
```java
// src/main/java/com/example/fetchconversationsapi1/dto/ConversationSummaryDto_FCAPI_1.java