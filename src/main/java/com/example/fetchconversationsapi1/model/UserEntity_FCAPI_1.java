package com.example.fetchconversationsapi1.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

/**
 * Represents a user in the system.
 */
@Entity
@Table(name = "app_user")
@Getter
@Setter
public class UserEntity_FCAPI_1 {
    @Id
    private UUID id;

    @Column(nullable = false)
    private String displayName;

    @Column
    private String avatarUrl;
}
```
```java
// src/main/java/com/example/fetchconversationsapi1/model/MessageEntity_FCAPI_1.java