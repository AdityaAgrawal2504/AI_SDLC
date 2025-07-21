package com.example.logininitiation.service;

import com.example.logininitiation.dto.LoginInitiationRequest_LIAPI_1001;
import com.example.logininitiation.dto.LoginInitiationResponse_LIAPI_1002;

/**
 * Handles the core logic of the authentication flow.
 */
public interface IAuthenticationService_LIAPI_6003 {

    /**
     * Orchestrates the login initiation process.
     * @param request The login request containing credentials.
     * @return A response containing a confirmation message and a correlation ID.
     */
    LoginInitiationResponse_LIAPI_1002 initiateLogin(LoginInitiationRequest_LIAPI_1001 request);
}
```
```java
// src/main/java/com/example/logininitiation/service/IOTPService_LIAPI_6002.java