package com.example.userregistration.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * Data Transfer Object for user registration request body.
 */
@Data
public class UserRegistrationRequest {

    @NotBlank(message = "Phone number is required.")
    @Pattern(regexp = "^\\d{10}$", message = "Phone number must be exactly 10 digits and contain no special characters or spaces.")
    private String phoneNumber;

    @NotBlank(message = "Password is required.")
    @Size(min = 8, max = 128, message = "Password must be at least 8 characters long.")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
             message = "Password must include at least one uppercase letter, one lowercase letter, one number, and one special character.")
    private String password;
}
```
```java
// src/main/java/com/example/userregistration/dto/UserRegistrationResponse.java