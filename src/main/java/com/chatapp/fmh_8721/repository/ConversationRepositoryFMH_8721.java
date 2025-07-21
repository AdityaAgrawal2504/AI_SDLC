package com.chatapp.fmh_8721.repository;

import com.chatapp.fmh_8721.domain.ConversationEntityFMH_8721;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ConversationRepositoryFMH_8721 extends JpaRepository<ConversationEntityFMH_8721, UUID> {

    /**
     * Checks if a user is a participant in a given conversation.
     * @param conversationId The ID of the conversation.
     * @param userId The ID of the user.
     * @return true if the user is a participant, false otherwise.
     */
    @Query("SELECT COUNT(c) > 0 FROM ConversationEntityFMH_8721 c JOIN c.participants p WHERE c.id = :conversationId AND p.id = :userId")
    boolean isUserParticipant(UUID conversationId, UUID userId);
    
    /**
     * Finds a conversation by its ID, fetching its participants.
     * @param id The ID of the conversation.
     * @return An Optional containing the conversation if found.
     */
    @Query("SELECT c FROM ConversationEntityFMH_8721 c LEFT JOIN FETCH c.participants WHERE c.id = :id")
    Optional<ConversationEntityFMH_8721> findByIdWithParticipants(UUID id);
}
```
```java
// src/main/java/com/chatapp/fmh_8721/repository/MessageRepositoryFMH_8721.java