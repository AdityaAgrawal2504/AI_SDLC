package com.example.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * Data Transfer Object for the login request payload.
 */
@Data
public class LoginRequestDTOAUTH {

    /**
     * The user's 10-digit phone number.
     */
    @NotBlank(message = "Phone number must not be null or empty")
    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be exactly 10 digits")
    public String phoneNumber;

    /**
     * The user's password.
     */
    @NotBlank(message = "Password must not be null or empty")
    @Size(min = 8, max = 128, message = "Password must be between 8 and 128 characters")
    public String password;
}
```
```java
// src/main/java/com/example/auth/dto/LoginSuccessResponseDTOAUTH.java