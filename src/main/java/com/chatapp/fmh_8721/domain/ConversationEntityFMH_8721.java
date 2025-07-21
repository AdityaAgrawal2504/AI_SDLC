package com.chatapp.fmh_8721.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * JPA Entity representing a conversation between users.
 */
@Entity
@Table(name = "conversations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConversationEntityFMH_8721 {

    @Id
    private UUID id;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "conversation_participants",
        joinColumns = @JoinColumn(name = "conversation_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<UserEntityFMH_8721> participants = new HashSet<>();
}
```
```java
// src/main/java/com/chatapp/fmh_8721/domain/MessageEntityFMH_8721.java