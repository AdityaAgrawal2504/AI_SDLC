package com.example.auth.exception;

import org.springframework.http.HttpStatus;

/**
 * Exception thrown when the external OTP service fails.
 */
public class OtpServiceException extends ApplicationException {
    public OtpServiceException(String message) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.OTP_SERVICE_FAILURE, message);
    }
}
```
src/main/java/com/example/auth/exception/RateLimitException.java
```java