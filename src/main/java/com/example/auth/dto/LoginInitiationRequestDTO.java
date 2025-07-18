package com.example.auth.dto;

import com.example.auth.util.E164PhoneNumber;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for the login initiation request body.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginInitiationRequestDTO {

    @NotBlank(message = "Phone number is required.")
    @E164PhoneNumber
    private String phoneNumber;

    @NotBlank(message = "Password is required.")
    @Size(min = 8, message = "Password must be at least 8 characters long.")
    private String password;
}
```
src/main/java/com/example/auth/dto/LoginInitiationResponseDTO.java
```java