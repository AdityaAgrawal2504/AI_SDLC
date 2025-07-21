package com.chatapp.fmh_8721.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * JPA Entity representing a user.
 */
@Entity
@Table(name = "app_users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserEntityFMH_8721 {

    @Id
    private UUID id;
    private String displayName;
    private String avatarUrl;
    private String password; // For security demo

    @ManyToMany(mappedBy = "participants")
    private Set<ConversationEntityFMH_8721> conversations = new HashSet<>();
}
```
```java
// src/main/java/com/chatapp/fmh_8721/domain/ConversationEntityFMH_8721.java