package com.example.fetchconversationsapi1.repository;

import com.example.fetchconversationsapi1.model.*;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Subquery;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.UUID;

/**
 * Provides JPA Specifications for dynamically building conversation queries.
 */
public class ConversationSpec_FCAPI_1 {

    /**
     * Creates a specification to find conversations for a specific user.
     */
    public static Specification<ConversationEntity_FCAPI_1> forAuthenticatedUser(UUID userId) {
        return (root, query, cb) -> {
            Join<ConversationEntity_FCAPI_1, ConversationParticipantEntity_FCAPI_1> participants = root.join("participants");
            return cb.equal(participants.get("user").get("id"), userId);
        };
    }

    /**
     * Creates a specification to filter conversations by the seen status of the last message for a specific user.
     */
    public static Specification<ConversationEntity_FCAPI_1> hasSeen(boolean seen, UUID userId) {
        return (root, query, cb) -> {
            Subquery<UUID> subquery = query.subquery(UUID.class);
            Join<ConversationEntity_FCAPI_1, ConversationParticipantEntity_FCAPI_1> participant = root.join("participants");
            
            Predicate userPredicate = cb.equal(participant.get("user").get("id"), userId);
            
            if (seen) {
                // isSeen = true means the user's lastSeenMessageId equals the conversation's lastMessageId
                return cb.and(userPredicate, cb.equal(root.get("lastMessage").get("id"), participant.get("lastSeenMessageId")));
            } else {
                // isSeen = false means they are not equal, or lastSeenMessageId is null
                return cb.and(userPredicate,
                    cb.or(
                        cb.notEqual(root.get("lastMessage").get("id"), participant.get("lastSeenMessageId")),
                        cb.isNull(participant.get("lastSeenMessageId"))
                    )
                );
            }
        };
    }

    /**
     * Creates a specification to filter conversations by a search query.
     * Searches against participant names and message content.
     */
    public static Specification<ConversationEntity_FCAPI_1> withSearchQuery(String searchQuery) {
        if (!StringUtils.hasText(searchQuery)) {
            return null;
        }
        String pattern = "%" + searchQuery.toLowerCase() + "%";
        return (root, query, cb) -> {
            // Ensure distinct conversations in case of multiple matches
            query.distinct(true);

            // Join to search participant display names
            Join<ConversationEntity_FCAPI_1, ConversationParticipantEntity_FCAPI_1> participantJoin = root.join("participants", JoinType.LEFT);
            Join<ConversationParticipantEntity_FCAPI_1, UserEntity_FCAPI_1> userJoin = participantJoin.join("user", JoinType.LEFT);
            Predicate participantNameMatch = cb.like(cb.lower(userJoin.get("displayName")), pattern);

            // Join to search message content
            Join<ConversationEntity_FCAPI_1, MessageEntity_FCAPI_1> messageJoin = root.join("messages", JoinType.LEFT);
            Predicate messageContentMatch = cb.like(cb.lower(messageJoin.get("content")), pattern);

            return cb.or(participantNameMatch, messageContentMatch);
        };
    }
}
```
```java
// src/main/java/com/example/fetchconversationsapi1/util/DtoMapper_FCAPI_1.java