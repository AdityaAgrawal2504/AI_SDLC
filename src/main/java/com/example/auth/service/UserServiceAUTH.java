package com.example.auth.service;

import com.example.auth.entity.UserAUTH;

/**
 * Interface for user-related operations like credential validation and account management.
 */
public interface UserServiceAUTH {
    /**
     * Validates user credentials and returns the user if successful.
     */
    UserAUTH validateCredentials(String phoneNumber, String password);
}
```
```java
// src/main/java/com/example/auth/service/UserServiceImplAUTH.java