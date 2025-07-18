package com.example.userregistration.service;

/**
 * Interface defining the contract for password handling services.
 */
public interface IPasswordService {
    
    /**
     * Hashes a plain-text password.
     */
    String hashPassword(String plainTextPassword);
}
```
```java
// src/main/java/com/example/userregistration/service/PasswordServiceImpl.java