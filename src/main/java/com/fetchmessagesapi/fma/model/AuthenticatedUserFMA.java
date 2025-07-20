package com.fetchmessagesapi.fma.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Represents the identity of a user after successful authentication.
 */
@Getter
@AllArgsConstructor
public class AuthenticatedUserFMA {
    private final String id;
    private final String name;
}
```
```java
// src/main/java/com/fetchmessagesapi/fma/model/MessageEntityFMA.java