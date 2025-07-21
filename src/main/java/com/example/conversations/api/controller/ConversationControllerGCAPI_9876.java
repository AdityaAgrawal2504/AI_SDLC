package com.example.conversations.api.controller;

import com.example.conversations.api.dto.PaginatedConversationsResponseGCAPI_9876;
import com.example.conversations.api.model.ConversationSortByGCAPI_9876;
import com.example.conversations.api.model.SortOrderGCAPI_9876;
import com.example.conversations.api.service.ConversationServiceGCAPI_9876;
import com.example.conversations.api.util.SecurityUtilGCAPI_9876;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * Handles incoming HTTP requests for retrieving conversations.
 */
@RestController
@RequestMapping("/conversations")
@RequiredArgsConstructor
@Validated
public class ConversationControllerGCAPI_9876 {

    private final ConversationServiceGCAPI_9876 conversationService;

    /**
     * Retrieves a paginated list of conversations for the authenticated user.
     * @param search Optional full-text search query.
     * @param sortBy Field to sort by. Defaults to 'last_message_time'.
     * @param sortOrder Sorting order. Defaults to 'desc'.
     * @param limit Maximum number of items per page.
     * @param cursor Cursor for the next page of results.
     * @return A paginated response of conversations.
     */
    @GetMapping
    public ResponseEntity<PaginatedConversationsResponseGCAPI_9876> getConversations(
        @RequestParam(required = false) @Size(max = 256, message = "Search query must not exceed 256 characters.") String search,
        @RequestParam(required = false, defaultValue = "last_message_time") ConversationSortByGCAPI_9876 sortBy,
        @RequestParam(required = false, defaultValue = "desc") SortOrderGCAPI_9876 sortOrder,
        @RequestParam(required = false, defaultValue = "${api.pagination.default-limit}") @Min(1) @Max(100) Integer limit,
        @RequestParam(required = false) String cursor
    ) {
        UUID authenticatedUserId = SecurityUtilGCAPI_9876.getAuthenticatedUserId();
        PaginatedConversationsResponseGCAPI_9876 response = conversationService.getConversations(
            authenticatedUserId, search, sortBy, sortOrder, limit, cursor
        );
        return ResponseEntity.ok(response);
    }
}
```
```java
// src/main/java/com/example/conversations/api/dto/ApiErrorDtoGCAPI_9876.java