package com.example.auth.controller;

import com.example.auth.dto.InitiateLoginRequestAUTH;
import com.example.auth.dto.InitiateLoginSuccessResponseAUTH;
import com.example.auth.service.AuthServiceAUTH;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for handling authentication endpoints.
 */
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthControllerAUTH {

    private final AuthServiceAUTH authService;

    /**
     * Handles the POST request to initiate a login process with phone and password.
     */
    @PostMapping("/initiate")
    public ResponseEntity<InitiateLoginSuccessResponseAUTH> initiatePhoneLogin(
            @Valid @RequestBody InitiateLoginRequestAUTH loginRequest) {
        InitiateLoginSuccessResponseAUTH response = authService.initiateLogin(loginRequest);
        return ResponseEntity.ok(response);
    }
}
```