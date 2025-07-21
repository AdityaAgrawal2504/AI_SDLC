package com.example.logininitiation.exception;

import com.example.logininitiation.enums.ErrorCode_LIAPI_2001;
import org.springframework.http.HttpStatus;

public class ResourceNotFoundException_LIAPI_3004 extends ApiException_LIAPI_3001 {
    
    public ResourceNotFoundException_LIAPI_3004(String message) {
        super(HttpStatus.NOT_FOUND, ErrorCode_LIAPI_2001.USER_NOT_FOUND, message);
    }
}
```
```java
// src/main/java/com/example/logininitiation/logging/LoggingAspect_LIAPI_9501.java