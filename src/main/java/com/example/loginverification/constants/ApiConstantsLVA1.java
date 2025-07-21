package com.example.loginverification.constants;

/**
 * Holds constant values used across the application.
 */
public final class ApiConstantsLVA1 {

    private ApiConstantsLVA1() {
        // Prevent instantiation
    }

    public static final String API_BASE_PATH = "/api";
    public static final String AUTH_PATH = "/auth";
    public static final String VERIFY_ENDPOINT = "/verify";

    public static final String PHONE_NUMBER_PATTERN = "^\\+[1-9]\\d{1,14}$";
    public static final String OTP_PATTERN = "^[0-9]{6}$";

    public static final String TOKEN_TYPE_BEARER = "Bearer";
    public static final long JWT_EXPIRATION_SECONDS = 3600; // 1 hour
}
```
```java
// src/main/java/com/example/loginverification/enums/ErrorCodeLVA1.java