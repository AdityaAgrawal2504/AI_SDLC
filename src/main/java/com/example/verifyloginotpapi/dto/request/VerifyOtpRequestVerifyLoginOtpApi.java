package com.example.verifyloginotpapi.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

/**
 * DTO for the OTP verification request body.
 * Contains validation rules based on the API specification.
 */
@Data
public class VerifyOtpRequestVerifyLoginOtpApi {

    @NotBlank(message = "Phone number is required.")
    @Pattern(regexp = "^\\+[1-9]\\d{1,14}$", message = "Phone number must be a valid E.164 formatted string (e.g., +14155552671).")
    private String phoneNumber;

    @NotBlank(message = "OTP code is required.")
    @Pattern(regexp = "^\\d{6}$", message = "OTP code must be a string containing exactly 6 digits.")
    private String otpCode;
}

//- FILE: src/main/java/com/example/verifyloginotpapi/dto/response/ApiErrorVerifyLoginOtpApi.java