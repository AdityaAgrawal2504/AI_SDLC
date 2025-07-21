package com.example.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for a successful login response.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginSuccessResponseDTOAUTH {

    /**
     * Confirmation message for the user.
     */
    public String message;

    /**
     * A unique identifier for this login attempt.
     */
    public String transactionId;
}
```
```java
// src/main/java/com/example/auth/dto/ErrorResponseAUTH.java