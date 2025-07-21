package com.example.logininitiation.repository;

import com.example.logininitiation.model.User_LIAPI_5001;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository_LIAPI_5002 extends JpaRepository<User_LIAPI_5001, String> {

    /**
     * Finds a user by their phone number.
     */
    Optional<User_LIAPI_5001> findByPhoneNumber(String phoneNumber);
}
```
```java
// src/main/java/com/example/logininitiation/service/IAuthenticationService_LIAPI_6003.java