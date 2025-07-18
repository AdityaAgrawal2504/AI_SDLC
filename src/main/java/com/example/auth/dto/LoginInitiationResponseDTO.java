package com.example.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for a successful login initiation response.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginInitiationResponseDTO {
    private String message;
    private String loginAttemptId;
}
```
src/main/java/com/example/auth/dto/ApiErrorDTO.java
```java