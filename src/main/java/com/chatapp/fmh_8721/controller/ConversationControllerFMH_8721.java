package com.chatapp.fmh_8721.controller;

import com.chatapp.fmh_8721.dto.FetchMessagesResponseFMH_8721;
import com.chatapp.fmh_8721.service.MessageServiceFMH_8721;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * REST controller for conversation-related endpoints.
 */
@RestController
@RequestMapping("/api/conversations")
@RequiredArgsConstructor
@Validated
public class ConversationControllerFMH_8721 {

    private final MessageServiceFMH_8721 messageService;
    private static final String UUID_REGEX = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$";

    /**
     * Retrieves paginated message history for a specific conversation.
     *
     * @param conversationId The unique identifier for the conversation.
     * @param limit The maximum number of messages to return.
     * @param before A cursor (message ID) to fetch messages created before it.
     * @return A ResponseEntity containing the list of messages and pagination info.
     */
    @GetMapping("/{conversationId}/messages")
    public ResponseEntity<FetchMessagesResponseFMH_8721> getMessageHistory(
            @PathVariable @Pattern(regexp = UUID_REGEX, message = "Path parameter 'conversationId' must be a valid UUID.")
            String conversationId,
            
            @RequestParam(defaultValue = "25")
            @Min(value = 1, message = "Query parameter 'limit' must be at least 1.")
            @Max(value = 100, message = "Query parameter 'limit' must not exceed 100.")
            int limit,
            
            @RequestParam(required = false)
            String before) {

        FetchMessagesResponseFMH_8721 response = messageService.fetchMessageHistory(
                UUID.fromString(conversationId), limit, before
        );
        return ResponseEntity.ok(response);
    }
}
```
```java
// src/main/java/com/chatapp/fmh_8721/util/DataInitializerFMH_8721.java