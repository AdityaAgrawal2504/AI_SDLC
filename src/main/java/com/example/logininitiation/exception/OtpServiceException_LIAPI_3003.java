package com.example.logininitiation.exception;

import com.example.logininitiation.enums.ErrorCode_LIAPI_2001;
import org.springframework.http.HttpStatus;

public class OtpServiceException_LIAPI_3003 extends ApiException_LIAPI_3001 {

    public OtpServiceException_LIAPI_3003(String message) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode_LIAPI_2001.OTP_SERVICE_FAILURE, message);
    }
}
```
```java
// src/main/java/com/example/logininitiation/exception/ResourceNotFoundException_LIAPI_3004.java