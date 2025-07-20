package com.fetchmessagesapi.fma.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Enumeration for message sorting fields.
 */
@Getter
@RequiredArgsConstructor
public enum SortByFMA {
    timestamp("timestamp"),
    status("status");

    private final String dbField;
}
```
```java
// src/main/java/com/fetchmessagesapi/fma/enums/SortOrderFMA.java