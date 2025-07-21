package com.example.fetchconversationsapi1.controller;

import com.example.fetchconversationsapi1.dto.PaginatedConversationsResponse_FCAPI_1;
import com.example.fetchconversationsapi1.enums.ConversationSortBy_FCAPI_1;
import com.example.fetchconversationsapi1.enums.SortOrder_FCAPI_1;
import com.example.fetchconversationsapi1.service.ConversationService_FCAPI_1;
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

/**
 * Controller for handling the Fetch Conversations API endpoint.
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Validated
public class FetchConversationsAPIController_1 {

    private final ConversationService_FCAPI_1 conversationService;

    /**
     * Handles GET requests to /api/conversations, retrieving a paginated list of conversations.
     */
    @GetMapping("/conversations")
    public ResponseEntity<PaginatedConversationsResponse_FCAPI_1> fetchConversations(
            @RequestParam(defaultValue = "1") @Min(value = 1, message = "Page must be a positive integer.")
            int page,

            @RequestParam(defaultValue = "20") @Min(value = 1, message = "Page size must be at least 1.") @Max(value = 100, message = "Page size cannot exceed 100.")
            int pageSize,

            @RequestParam(defaultValue = "lastMessageTime")
            ConversationSortBy_FCAPI_1 sortBy,

            @RequestParam(defaultValue = "desc")
            SortOrder_FCAPI_1 sortOrder,

            @RequestParam(required = false)
            Boolean seen,

            @RequestParam(required = false) @Size(max = 255, message = "Search query must not exceed 255 characters.")
            String searchQuery) {

        PaginatedConversationsResponse_FCAPI_1 response = conversationService.fetchConversations(
                page, pageSize, sortBy, sortOrder, seen, searchQuery);
        return ResponseEntity.ok(response);
    }
}
```
```java
// src/main/java/com/example/fetchconversationsapi1/exception/GlobalExceptionHandler_FCAPI_1.java