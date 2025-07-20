package com.example.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * Represents the request payload for initiating a phone login.
 */
@Data
public class InitiateLoginRequestAUTH {
    @NotBlank(message = "The phoneNumber field is required.")
    @Pattern(regexp = "^\\d{10}$", message = "Phone number must be exactly 10 digits.")
    private String phoneNumber;

    @NotBlank(message = "The password field is required.")
    @Size(min = 8, message = "Password must be at least 8 characters long.")
    private String password;
}
```
```java
// src/main/java/com/example/auth/dto/InitiateLoginSuccessResponseAUTH.java