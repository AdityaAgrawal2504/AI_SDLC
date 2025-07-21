package com.chatapp.fmh_8721.repository;

import com.chatapp.fmh_8721.domain.MessageEntityFMH_8721;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Repository
public interface MessageRepositoryFMH_8721 extends JpaRepository<MessageEntityFMH_8721, String> {

    /**
     * Fetches a paginated list of messages for a conversation, ordered newest first.
     * This is used for the first page request when no 'before' cursor is provided.
     * @param conversationId The ID of the conversation.
     * @param pageable The pagination information (limit).
     * @return A list of message entities.
     */
    @Query("SELECT m FROM MessageEntityFMH_8721 m JOIN FETCH m.author WHERE m.conversationId = :conversationId ORDER BY m.createdAt DESC")
    List<MessageEntityFMH_8721> findByConversationIdOrderByCreatedAtDesc(UUID conversationId, Pageable pageable);

    /**
     * Fetches a paginated list of messages for a conversation created before a given timestamp.
     * This is used for subsequent pages when a 'before' cursor is provided.
     * @param conversationId The ID of the conversation.
     * @param cursorTimestamp The timestamp from the cursor message.
     * @param pageable The pagination information (limit).
     * @return A list of message entities.
     */
    @Query("SELECT m FROM MessageEntityFMH_8721 m JOIN FETCH m.author WHERE m.conversationId = :conversationId AND m.createdAt < :cursorTimestamp ORDER BY m.createdAt DESC")
    List<MessageEntityFMH_8721> findByConversationIdAndCreatedAtBeforeOrderByCreatedAtDesc(UUID conversationId, Instant cursorTimestamp, Pageable pageable);
}
```
```java
// src/main/java/com/chatapp/fmh_8721/repository/UserRepositoryFMH_8721.java