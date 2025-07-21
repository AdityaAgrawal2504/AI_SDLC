package com.example.logininitiation.dto;

import com.example.logininitiation.enums.ErrorCode_LIAPI_2001;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiError_LIAPI_1003 {
    /**
     * A unique, machine-readable code for the specific error.
     */
    private String errorCode;

    /**
     * A developer-friendly message detailing the error.
     */
    private String message;

    public ApiError_LIAPI_1003(ErrorCode_LIAPI_2001 errorCode, String message) {
        this.errorCode = errorCode.getCode();
        this.message = message;
    }
}
```
```java
// src/main/java/com/example/logininitiation/dto/LoginInitiationRequest_LIAPI_1001.java