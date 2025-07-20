package com.fetchmessagesapi.fma.controller;

import com.fetchmessagesapi.fma.dto.MessageListResponseFMA;
import com.fetchmessagesapi.fma.enums.SortByFMA;
import com.fetchmessagesapi.fma.enums.SortOrderFMA;
import com.fetchmessagesapi.fma.enums.StatusFilterFMA;
import com.fetchmessagesapi.fma.security.AuthenticatedUserIdFMA;
import com.fetchmessagesapi.fma.service.IMessageFetchOptionsFMA;
import com.fetchmessagesapi.fma.service.IMessageServiceFMA;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller to handle fetching messages for the authenticated user.
 */
@RestController
@RequestMapping("/messages")
@RequiredArgsConstructor
@Validated
@Tag(name = "Messaging", description = "Endpoints for user message management")
public class MessageControllerFMA {

    private final IMessageServiceFMA messageService;

    /**
     * Retrieves a paginated list of messages based on provided filter, sort, and search criteria.
     * @param authenticatedUserId The ID of the currently logged-in user, injected by a custom resolver.
     * @param search Optional search query for subject or body.
     * @param sortBy Field to sort by.
     * @param sortOrder Sorting direction.
     * @param status Message status to filter by.
     * @param limit Number of messages per page.
     * @param offset Page offset.
     * @return A response entity containing the list of messages and pagination info.
     */
    @GetMapping
    @Operation(
        operationId = "fetchUserMessages",
        summary = "Fetch messages for the authenticated user",
        description = "Retrieves a paginated list of messages for the currently logged-in user. " +
                      "Supports filtering by status, sorting, and text-based searching.",
        parameters = {
            @Parameter(
                name = "Authorization",
                in = ParameterIn.HEADER,
                description = "Bearer token for user authentication (e.g., 'Bearer <token>').",
                required = true,
                schema = @Schema(type = "string", pattern = "^Bearer .+$")
            )
        },
        responses = {
            @ApiResponse(responseCode = "200", description = "A paginated list of messages was successfully retrieved.",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageListResponseFMA.class))),
            @ApiResponse(responseCode = "400", description = "The request was malformed due to invalid query parameters.",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = com.fetchmessagesapi.fma.dto.ErrorResponseFMA.class))),
            @ApiResponse(responseCode = "401", description = "Authentication token is missing, malformed, or invalid.",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = com.fetchmessagesapi.fma.dto.ErrorResponseFMA.class))),
            @ApiResponse(responseCode = "500", description = "An unexpected error occurred on the server.",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = com.fetchmessagesapi.fma.dto.ErrorResponseFMA.class)))
        }
    )
    public ResponseEntity<MessageListResponseFMA> fetchMessages(
        @Parameter(hidden = true) @AuthenticatedUserIdFMA String authenticatedUserId,
        @RequestHeader(name = "Authorization") String authorizationHeader, // Only for OpenAPI documentation
        @Parameter(description = "Search query to filter messages by subject or body content.")
        @RequestParam(required = false) @Size(min = 3, max = 100) String search,
        @Parameter(description = "Field to sort the messages by.")
        @RequestParam(required = false, defaultValue = "timestamp") SortByFMA sortBy,
        @Parameter(description = "Order of sorting.")
        @RequestParam(required = false, defaultValue = "desc") SortOrderFMA sortOrder,
        @Parameter(description = "Filter messages by their read status.")
        @RequestParam(required = false, defaultValue = "all") StatusFilterFMA status,
        @Parameter(description = "Maximum number of messages to return per page.")
        @RequestParam(required = false, defaultValue = "25") @Min(1) @Max(100) int limit,
        @Parameter(description = "Number of messages to skip for pagination.")
        @RequestParam(required = false, defaultValue = "0") @Min(0) int offset) {

        IMessageFetchOptionsFMA options = new IMessageFetchOptionsFMA(
            authenticatedUserId,
            search,
            sortBy,
            sortOrder,
            status,
            limit,
            offset
        );

        MessageListResponseFMA response = messageService.fetchMessages(options);
        return ResponseEntity.ok(response);
    }
}
```
```java
// src/main/java/com/fetchmessagesapi/fma/controller/resolver/UserIdArgumentResolverFMA.java