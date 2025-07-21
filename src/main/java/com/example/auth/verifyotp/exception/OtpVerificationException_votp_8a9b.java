package com.example.auth.verifyotp.exception;

import com.example.auth.verifyotp.config.ErrorCode_votp_8a9b;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Custom runtime exception for handling specific OTP verification failures.
 * It holds an ErrorCode to facilitate structured error responses.
 */
@Getter
public class OtpVerificationException_votp_8a9b extends RuntimeException {

    private final ErrorCode_votp_8a9b errorCode;
    private final HttpStatus httpStatus;

    /**
     * Constructs a new exception with a specific error code.
     * @param errorCode The ErrorCode enum constant representing the failure.
     */
    public OtpVerificationException_votp_8a9b(ErrorCode_votp_8a9b errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.httpStatus = errorCode.getHttpStatus();
    }
}
```
```java