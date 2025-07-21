package com.example.loginverification.controller;

import com.example.loginverification.constants.ApiConstantsLVA1;
import com.example.loginverification.dto.LoginVerificationRequestLVA1;
import com.example.loginverification.dto.LoginVerificationResponseLVA1;
import com.example.loginverification.service.LoginVerificationServiceLVA1;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for the Login Verification API.
 */
@RestController
@RequestMapping(ApiConstantsLVA1.API_BASE_PATH + ApiConstantsLVA1.AUTH_PATH)
@RequiredArgsConstructor
public class LoginVerificationControllerLVA1 {

    private final LoginVerificationServiceLVA1 loginVerificationService;

    /**
     * Handles the POST request to verify a user's phone number and OTP.
     * @param request The request body containing the phone number and OTP.
     * @return A ResponseEntity with the auth token on success, or an error response.
     */
    @PostMapping(ApiConstantsLVA1.VERIFY_ENDPOINT)
    public ResponseEntity<LoginVerificationResponseLVA1> verifyLogin(@Valid @RequestBody LoginVerificationRequestLVA1 request) {
        LoginVerificationResponseLVA1 response = loginVerificationService.verifyLogin(request);
        return ResponseEntity.ok(response);
    }
}
```