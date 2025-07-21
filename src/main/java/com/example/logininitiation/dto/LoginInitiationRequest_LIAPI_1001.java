package com.example.logininitiation.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginInitiationRequest_LIAPI_1001 {

    /**
     * The user's 10-digit phone number.
     */
    @NotNull(message = "Phone number must be present.")
    @Pattern(regexp = "^\\d{10}$", message = "Phone number must be exactly 10 digits.")
    private String phoneNumber;

    /**
     * The user's plaintext password for authentication.
     */
    @NotNull(message = "Password must be present.")
    @Size(min = 8, max = 128, message = "Password must be between 8 and 128 characters long.")
    private String password;
}
```
```java
// src/main/java/com/example/logininitiation/dto/LoginInitiationResponse_LIAPI_1002.java