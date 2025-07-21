package com.example.logininitiation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginInitiationResponse_LIAPI_1002 {
    /**
     * A confirmation message for the user.
     */
    private String message;

    /**
     * The unique token to correlate this initiation with a subsequent OTP verification.
     */
    private String correlationId;
}
```
```java
// src/main/java/com/example/logininitiation/enums/ErrorCode_LIAPI_2001.java