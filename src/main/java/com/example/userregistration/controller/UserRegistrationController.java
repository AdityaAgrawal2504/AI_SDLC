package com.example.userregistration.controller;

import com.example.userregistration.dto.UserRegistrationRequest;
import com.example.userregistration.dto.UserRegistrationResponse;
import com.example.userregistration.service.IUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserRegistrationController {

    private final IUserService userService;

    /**
     * Handles the POST request to register a new user.
     * Validates the request body and calls the user service to perform registration.
     */
    @PostMapping("/register")
    public ResponseEntity<UserRegistrationResponse> registerUser(@Valid @RequestBody UserRegistrationRequest registrationRequest) {
        UserRegistrationResponse response = userService.registerUser(registrationRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
```
```java
// src/main/java/com/example/userregistration/util/logging/StructuredLogger.java