package com.fetchmessagesapi.fma.service;

import com.fetchmessagesapi.fma.enums.SortByFMA;
import com.fetchmessagesapi.fma.enums.SortOrderFMA;
import com.fetchmessagesapi.fma.enums.StatusFilterFMA;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Represents the consolidated and validated options for fetching messages.
 * This acts as a data carrier between the controller and service layers.
 */
@Getter
@RequiredArgsConstructor
public class IMessageFetchOptionsFMA {
    private final String authenticatedUserId;
    private final String searchQuery;
    private final SortByFMA sortBy;
    private final SortOrderFMA sortOrder;
    private final StatusFilterFMA status;
    private final int limit;
    private final int offset;
}
```
```java
// src/main/java/com/fetchmessagesapi/fma/service/IMessageServiceFMA.java