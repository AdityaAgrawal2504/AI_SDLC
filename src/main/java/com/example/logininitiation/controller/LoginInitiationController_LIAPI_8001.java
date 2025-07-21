package com.example.logininitiation.controller;

import com.example.logininitiation.dto.LoginInitiationRequest_LIAPI_1001;
import com.example.logininitiation.dto.LoginInitiationResponse_LIAPI_1002;
import com.example.logininitiation.service.IAuthenticationService_LIAPI_6003;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class LoginInitiationController_LIAPI_8001 {

    private final IAuthenticationService_LIAPI_6003 authenticationService;

    /**
     * Handles the POST request to initiate the login process.
     * Validates the request body and delegates to the authentication service.
     */
    @PostMapping("/initiate")
    public ResponseEntity<LoginInitiationResponse_LIAPI_1002> initiateLogin(
        @Valid @RequestBody LoginInitiationRequest_LIAPI_1001 request) {

        LoginInitiationResponse_LIAPI_1002 response = authenticationService.initiateLogin(request);
        return ResponseEntity.ok(response);
    }
}
```
```java
// src/main/java/com/example/logininitiation/dto/ApiError_LIAPI_1003.java