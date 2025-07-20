package com.example.auth.service;

import com.example.auth.dto.InitiateLoginRequestAUTH;
import com.example.auth.dto.InitiateLoginSuccessResponseAUTH;

/**
 * Interface defining the primary authentication flow logic.
 */
public interface AuthServiceAUTH {
    /**
     * Initiates the login process, validates credentials, and triggers OTP sending.
     */
    InitiateLoginSuccessResponseAUTH initiateLogin(InitiateLoginRequestAUTH request);
}
```
```java
// src/main/java/com/example/auth/service/AuthServiceImplAUTH.java