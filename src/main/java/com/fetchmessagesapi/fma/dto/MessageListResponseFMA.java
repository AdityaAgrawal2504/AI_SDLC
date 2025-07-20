package com.fetchmessagesapi.fma.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * DTO for the top-level response when fetching a list of messages.
 */
@Getter
@AllArgsConstructor
public class MessageListResponseFMA {
    private final List<MessageFMA> data;
    private final PaginationInfoFMA pagination;
}
```
```java
// src/main/java/com/fetchmessagesapi/fma/dto/PaginationInfoFMA.java