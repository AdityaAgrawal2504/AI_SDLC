package com.example.auth.lwc8765.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginCredentialsRequestLWC_8765 {

    @NotNull(message = "The 'phoneNumber' field is mandatory.")
    @Pattern(regexp = "^\\d{10}$", message = "The 'phoneNumber' must consist of exactly 10 digits.")
    private String phoneNumber;

    @NotNull(message = "The 'password' field is mandatory.")
    @Size(min = 8, message = "The 'password' must be at least 8 characters long.")
    private String password;
}
```
```java
// src/main/java/com/example/auth/lwc8765/dto/response/LoginInitiatedResponseLWC_8765.java