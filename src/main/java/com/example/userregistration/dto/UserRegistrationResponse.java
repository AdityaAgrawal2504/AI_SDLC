package com.example.userregistration.dto;

import lombok.Builder;
import lombok.Data;
import java.time.Instant;
import java.util.UUID;

/**
 * Data Transfer Object for a successful user registration response.
 */
@Data
@Builder
public class UserRegistrationResponse {
    private UUID userId;
    private String message;
    private Instant createdAt;
}
```
```java
// src/main/java/com/example/userregistration/dto/ErrorResponse.java