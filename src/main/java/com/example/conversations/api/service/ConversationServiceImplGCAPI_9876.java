package com.example.conversations.api.service;

import com.example.conversations.api.dto.*;
import com.example.conversations.api.exception.CustomApiExceptionGCAPI_9876;
import com.example.conversations.api.model.ConversationSortByGCAPI_9876;
import com.example.conversations.api.model.ErrorCodeGCAPI_9876;
import com.example.conversations.api.model.MessageContentTypeGCAPI_9876;
import com.example.conversations.api.model.SortOrderGCAPI_9876;
import com.example.conversations.api.util.CursorUtilGCAPI_9876;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Implementation of the ConversationService containing the business logic.
 * <p>
 * mermaid
 * sequenceDiagram
 *     participant C as ConversationController
 *     participant S as ConversationService
 *     participant U as CursorUtil
 *     participant R as Repository (Mocked)
 *     C->>S: getConversations(params)
 *     S->>U: decode(cursor)
 *     U-->>S: decodedCursor
 *     S->>R: findByCriteria(userId, params, decodedCursor)
 *     R-->>S: List&lt;Conversation&gt;, totalCount
 *     S->>S: Map results to DTOs
 *     S->>U: encode(lastItem)
 *     U-->>S: nextCursor
 *     S->>C: PaginatedConversationsResponse
 */
@Service
@RequiredArgsConstructor
public class ConversationServiceImplGCAPI_9876 implements ConversationServiceGCAPI_9876 {

    private final CursorUtilGCAPI_9876 cursorUtil;

    /**
     * Fetches conversations, applying filtering, sorting, and pagination.
     * This implementation uses a mock data source for demonstration.
     */
    @Override
    public PaginatedConversationsResponseGCAPI_9876 getConversations(
        UUID userId, String search, ConversationSortByGCAPI_9876 sortBy,
        SortOrderGCAPI_9876 sortOrder, int limit, String cursorStr
    ) {
        // 1. Decode cursor
        Map<String, String> cursor = cursorUtil.decode(cursorStr);
        if (cursorStr != null && cursor.isEmpty()) {
            throw new CustomApiExceptionGCAPI_9876(HttpStatus.BAD_REQUEST, ErrorCodeGCAPI_9876.INVALID_CURSOR, "The provided cursor is malformed or invalid.");
        }

        // 2. Fetch data from the persistence layer (mocked here)
        // In a real application, this would be a call to a repository/DAO method.
        // e.g., conversationRepository.findWithFilters(userId, search, sortBy, sortOrder, limit, cursor)
        List<ConversationDtoGCAPI_9876> allConversations = getMockConversations(userId);
        long totalCount = allConversations.size();

        // 3. Apply filtering and sorting
        List<ConversationDtoGCAPI_9876> filteredAndSorted = applyFilteringAndSorting(allConversations, search, sortBy, sortOrder, cursor);

        // 4. Apply pagination (limit)
        List<ConversationDtoGCAPI_9876> pageData = filteredAndSorted.stream().limit(limit).collect(Collectors.toList());

        // 5. Determine next cursor
        String nextCursor = null;
        if (pageData.size() == limit && filteredAndSorted.size() > limit) {
             ConversationDtoGCAPI_9876 lastItem = pageData.get(pageData.size() - 1);
             Map<String, String> nextCursorData = new HashMap<>();
             nextCursorData.put("id", lastItem.getId().toString());
             if (sortBy == ConversationSortByGCAPI_9876.LAST_MESSAGE_TIME) {
                 nextCursorData.put("lastMessageTime", lastItem.getLastMessage().getCreatedAt().toString());
             } else if (sortBy == ConversationSortByGCAPI_9876.IS_READ) {
                 nextCursorData.put("isRead", String.valueOf(lastItem.isRead()));
             }
             nextCursor = cursorUtil.encode(nextCursorData);
        }

        // 6. Build and return the response DTO
        return PaginatedConversationsResponseGCAPI_9876.builder()
            .data(pageData)
            .pagination(PaginationInfoGCAPI_9876.builder()
                .totalCount(totalCount)
                .limit(limit)
                .nextCursor(nextCursor)
                .hasMore(nextCursor != null)
                .build())
            .build();
    }

    private List<ConversationDtoGCAPI_9876> applyFilteringAndSorting(List<ConversationDtoGCAPI_9876> conversations, String search, ConversationSortByGCAPI_9876 sortBy, SortOrderGCAPI_9876 sortOrder, Map<String, String> cursor) {
         // --- Filter by search term ---
        List<ConversationDtoGCAPI_9876> filtered = conversations.stream()
            .filter(c -> search == null || c.getLastMessage().getContent().toLowerCase().contains(search.toLowerCase()))
            .collect(Collectors.toList());

        // --- Sort ---
        Comparator<ConversationDtoGCAPI_9876> comparator;
        if (sortBy == ConversationSortByGCAPI_9876.IS_READ) {
            comparator = Comparator.comparing(ConversationDtoGCAPI_9876::isRead)
                                    .thenComparing(c -> c.getLastMessage().getCreatedAt()); // Secondary sort
        } else { // Default to last_message_time
            comparator = Comparator.comparing(c -> c.getLastMessage().getCreatedAt());
        }

        if (sortOrder == SortOrderGCAPI_9876.DESC) {
            comparator = comparator.reversed();
        }
        // Add ID as a final tie-breaker for stable sorting
        comparator = comparator.thenComparing(ConversationDtoGCAPI_9876::getId);
        
        filtered.sort(comparator);
        
        // --- Apply cursor ---
        if (cursor.isEmpty()) {
            return filtered;
        }

        int startIndex = -1;
        String cursorId = cursor.get("id");

        for (int i = 0; i < filtered.size(); i++) {
            if (filtered.get(i).getId().toString().equals(cursorId)) {
                startIndex = i + 1;
                break;
            }
        }
        
        if (startIndex != -1 && startIndex < filtered.size()) {
            return filtered.subList(startIndex, filtered.size());
        }

        return Collections.emptyList();
    }

    /**
     * Generates a list of mock conversations for demonstration purposes.
     */
    private List<ConversationDtoGCAPI_9876> getMockConversations(UUID currentUserId) {
        return IntStream.range(0, 50).mapToObj(i -> {
            boolean isRead = i % 3 == 0;
            OffsetDateTime lastMessageTime = OffsetDateTime.now(ZoneOffset.UTC).minusHours(i);
            UUID conversationId = UUID.randomUUID();
            UUID otherUserId = UUID.randomUUID();

            ParticipantDtoGCAPI_9876 currentUserParticipant = ParticipantDtoGCAPI_9876.builder()
                .userId(currentUserId)
                .displayName("You")
                .avatarUrl("https://example.com/avatar/me.png")
                .build();
            ParticipantDtoGCAPI_9876 otherUserParticipant = ParticipantDtoGCAPI_9876.builder()
                .userId(otherUserId)
                .displayName("User " + i)
                .avatarUrl("https://example.com/avatar/" + i + ".png")
                .build();

            return ConversationDtoGCAPI_9876.builder()
                .id(conversationId)
                .title(i % 5 == 0 ? "Group Chat " + i : null) // Some are group chats
                .participants(List.of(currentUserParticipant, otherUserParticipant))
                .lastMessage(MessageDtoGCAPI_9876.builder()
                    .id(UUID.randomUUID())
                    .senderId(otherUserId)
                    .content("This is the last message for conversation " + i + ". Some have keywords like 'important' or 'urgent'.")
                    .contentType(MessageContentTypeGCAPI_9876.TEXT)
                    .createdAt(lastMessageTime)
                    .build())
                .unreadCount(isRead ? 0 : (i % 5 + 1))
                .isRead(isRead)
                .createdAt(OffsetDateTime.now(ZoneOffset.UTC).minusDays(i))
                .updatedAt(lastMessageTime)
                .build();
        }).collect(Collectors.toList());
    }
}
```
```java
// src/main/java/com/example/conversations/api/util/CursorUtilGCAPI_9876.java