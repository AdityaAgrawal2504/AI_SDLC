package com.example.fetchconversationsapi1.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

/**
 * Join entity representing a user's participation in a conversation.
 * It also stores user-specific state for that conversation.
 */
@Entity
@Table(name = "conversation_participant")
@Getter
@Setter
public class ConversationParticipantEntity_FCAPI_1 {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conversation_id", nullable = false)
    private ConversationEntity_FCAPI_1 conversation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity_FCAPI_1 user;

    @Column(name = "last_seen_message_id")
    private UUID lastSeenMessageId;
}
```
```java
// src/main/java/com/example/fetchconversationsapi1/model/ConversationEntity_FCAPI_1.java