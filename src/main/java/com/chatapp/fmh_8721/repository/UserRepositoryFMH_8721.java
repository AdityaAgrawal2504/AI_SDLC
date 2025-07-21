package com.chatapp.fmh_8721.repository;

import com.chatapp.fmh_8721.domain.UserEntityFMH_8721;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepositoryFMH_8721 extends JpaRepository<UserEntityFMH_8721, UUID> {
    Optional<UserEntityFMH_8721> findByDisplayName(String displayName);
}
```
```java
// src/main/java/com/chatapp/fmh_8721/mapper/MessageMapperFMH_8721.java