package com.example.auth.api;

import com.example.auth.dto.LoginInitiationRequestDTO;
import com.example.auth.dto.LoginInitiationResponseDTO;
import com.example.auth.service.IAuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST Controller for handling authentication-related endpoints.
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final IAuthenticationService authenticationService;

    /**
     * Handles the first step of the login process. It accepts a phone number and password,
     * validates them, and if correct, triggers an OTP to be sent to the user.
     *
     * @param request The login initiation request DTO containing credentials.
     * @return A ResponseEntity with a success message and a login attempt ID.
     */
    @PostMapping("/login")
    public ResponseEntity<LoginInitiationResponseDTO> initiateLogin(@Valid @RequestBody LoginInitiationRequestDTO request) {
        LoginInitiationResponseDTO response = authenticationService.initiateLogin(request);
        return ResponseEntity.ok(response);
    }
}
```
src/main/java/com/example/auth/config/DataLoader.java
```java