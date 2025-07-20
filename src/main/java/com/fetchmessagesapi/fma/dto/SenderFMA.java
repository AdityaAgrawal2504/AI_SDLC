package com.fetchmessagesapi.fma.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

/**
 * DTO representing the sender of a message.
 */
@Getter
@AllArgsConstructor
public class SenderFMA {
    private final UUID id;
    private final String name;
}
```
```java
// src/main/java/com/fetchmessagesapi/fma/enums/ApiErrorFMA.java