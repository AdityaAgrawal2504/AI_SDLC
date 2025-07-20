package com.fetchmessagesapi.fma.dto;

import lombok.Builder;
import lombok.Getter;

/**
 * DTO containing pagination metadata for a list response.
 */
@Getter
@Builder
public class PaginationInfoFMA {
    private final long totalItems;
    private final int limit;
    private final int offset;
    private final int currentItemCount;
}
```
```java
// src/main/java/com/fetchmessagesapi/fma/dto/SenderFMA.java