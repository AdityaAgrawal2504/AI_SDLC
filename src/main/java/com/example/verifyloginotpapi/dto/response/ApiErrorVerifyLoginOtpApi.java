package com.example.verifyloginotpapi.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

/**
 * Standardized error response payload.
 */
@Data
@AllArgsConstructor
public class ApiErrorVerifyLoginOtpApi {
    private int httpStatus;
    private String errorCode;
    private String errorMessage;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC")
    private Instant timestamp;
}

//- FILE: src/main/java/com/example/verifyloginotpapi/dto/response/VerifyOtpSuccessResponseVerifyLoginOtpApi.java