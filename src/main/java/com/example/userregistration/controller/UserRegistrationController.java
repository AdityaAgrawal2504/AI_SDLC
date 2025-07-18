package com.example.userregistration.controller;

import com.example.userregistration.dto.UserRegistrationRequest;
import com.example.userregistration.dto.UserRegistrationSuccessResponse;
import com.example.userregistration.service.IUserRegistrationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for handling user registration requests.
 */
@RestController
@RequestMapping("/register")
@RequiredArgsConstructor
public class UserRegistrationController {

    private final IUserRegistrationService userRegistrationService;

    /**
     * Registers a new user account using a 10-digit phone number and a password.
     * @param request The user registration request payload, which is automatically validated.
     * @return A ResponseEntity with the user's unique identifier on success (201 Created).
     */
    @PostMapping
    public ResponseEntity<UserRegistrationSuccessResponse> registerUser(@Valid @RequestBody UserRegistrationRequest request) {
        UserRegistrationSuccessResponse response = userRegistrationService.registerUser(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}


// src/main/java/com/example/userregistration/exception/GlobalExceptionHandler.java