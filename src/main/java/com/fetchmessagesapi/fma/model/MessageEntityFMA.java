package com.fetchmessagesapi.fma.model;

import com.fetchmessagesapi.fma.enums.MessageStatusFMA;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import java.time.Instant;
import java.util.UUID;

/**
 * JPA Entity representing a message in the database.
 */
@Entity
@Table(name = "messages_fma")
@Getter
@Setter
public class MessageEntityFMA {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(nullable = false)
    private UUID conversationId;

    @Column(nullable = false)
    private UUID recipientId; // The user this message is for

    @Column(nullable = false)
    private UUID senderId;

    @Column(nullable = false)
    private String senderName;

    private String subject;

    @Lob
    private String body;

    @Column(nullable = false)
    private Instant timestamp;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MessageStatusFMA status;
}
```
```java
// src/main/java/com/fetchmessagesapi/fma/repository/MessageRepositoryFMA.java