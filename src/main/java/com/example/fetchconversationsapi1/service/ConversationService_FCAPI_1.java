package com.example.fetchconversationsapi1.service;

import com.example.fetchconversationsapi1.dto.PaginatedConversationsResponse_FCAPI_1;
import com.example.fetchconversationsapi1.dto.PaginationInfoDto_FCAPI_1;
import com.example.fetchconversationsapi1.enums.ConversationSortBy_FCAPI_1;
import com.example.fetchconversationsapi1.enums.SortOrder_FCAPI_1;
import com.example.fetchconversationsapi1.model.ConversationEntity_FCAPI_1;
import com.example.fetchconversationsapi1.repository.ConversationRepository_FCAPI_1;
import com.example.fetchconversationsapi1.repository.ConversationSpec_FCAPI_1;
import com.example.fetchconversationsapi1.security.AuthenticationFacade_FCAPI_1;
import com.example.fetchconversationsapi1.util.DtoMapper_FCAPI_1;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service layer containing business logic for fetching conversations.
 */
@Service
@RequiredArgsConstructor
public class ConversationService_FCAPI_1 {

    private final ConversationRepository_FCAPI_1 conversationRepository;
    private final AuthenticationFacade_FCAPI_1 authenticationFacade;

    /**
     * Retrieves a paginated and filtered list of conversations for the authenticated user.
     */
    @Transactional(readOnly = true)
    public PaginatedConversationsResponse_FCAPI_1 fetchConversations(
            int page, int pageSize, ConversationSortBy_FCAPI_1 sortBy, SortOrder_FCAPI_1 sortOrder,
            Boolean seen, String searchQuery) {

        UUID currentUserId = authenticationFacade.getAuthenticatedUserId();

        // 1. Build Sorting
        Sort sort = Sort.by(Sort.Direction.fromString(sortOrder.name()), sortBy.getDbField());
        
        // 2. Build Pageable object (adjusting for 1-based page number)
        Pageable pageable = PageRequest.of(page - 1, pageSize, sort);

        // 3. Build dynamic query using Specifications
        Specification<ConversationEntity_FCAPI_1> spec = Specification.where(ConversationSpec_FCAPI_1.forAuthenticatedUser(currentUserId));

        if (searchQuery != null) {
            spec = spec.and(ConversationSpec_FCAPI_1.withSearchQuery(searchQuery));
        }
        if (seen != null) {
            spec = spec.and(ConversationSpec_FCAPI_1.hasSeen(seen, currentUserId));
        }

        // 4. Execute query
        Page<ConversationEntity_FCAPI_1> conversationPage = conversationRepository.findAll(spec, pageable);

        // 5. Map to DTOs
        var conversationDtos = conversationPage.getContent().stream()
                .map(entity -> DtoMapper_FCAPI_1.toConversationSummaryDto(entity, currentUserId))
                .collect(Collectors.toList());
        
        var paginationInfo = new PaginationInfoDto_FCAPI_1(
                conversationPage.getNumber() + 1, // Return 1-based page
                conversationPage.getSize(),
                conversationPage.getTotalPages(),
                conversationPage.getTotalElements()
        );

        return new PaginatedConversationsResponse_FCAPI_1(conversationDtos, paginationInfo);
    }
}
```
```java
// src/main/java/com/example/fetchconversationsapi1/controller/FetchConversationsAPIController_1.java