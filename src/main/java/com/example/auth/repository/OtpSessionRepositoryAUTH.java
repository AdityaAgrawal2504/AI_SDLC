package com.example.auth.repository;

import com.example.auth.entity.OtpSessionAUTH;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for OtpSessionAUTH entities.
 */
@Repository
public interface OtpSessionRepositoryAUTH extends JpaRepository<OtpSessionAUTH, String> {
}
```
```java
// src/main/java/com/example/auth/exception/AuthServiceExceptionAUTH.java