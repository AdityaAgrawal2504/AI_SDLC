package com.fetchmessagesapi.fma.repository;

import com.fetchmessagesapi.fma.model.MessageEntityFMA;
import com.fetchmessagesapi.fma.service.IMessageFetchOptionsFMA;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Utility class to build dynamic JPA Specifications for message filtering.
 */
public class MessageSpecificationFMA {

    /**
     * Creates a JPA Specification based on the provided fetching options.
     * @param options The consolidated filter and search criteria.
     * @return A Specification object for querying MessageEntityFMA.
     */
    public static Specification<MessageEntityFMA> from(IMessageFetchOptionsFMA options) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Always filter by the authenticated user's ID.
            predicates.add(criteriaBuilder.equal(root.get("recipientId"), UUID.fromString(options.getAuthenticatedUserId())));

            // Add status filter if not 'all'.
            switch (options.getStatus()) {
                case read -> predicates.add(criteriaBuilder.equal(root.get("status"), com.fetchmessagesapi.fma.enums.MessageStatusFMA.read));
                case unread -> predicates.add(criteriaBuilder.equal(root.get("status"), com.fetchmessagesapi.fma.enums.MessageStatusFMA.unread));
                // 'all' case does not add a predicate.
            }

            // Add text search filter if a query is provided.
            if (StringUtils.hasText(options.getSearchQuery())) {
                String likePattern = "%" + options.getSearchQuery().toLowerCase() + "%";
                Predicate subjectLike = criteriaBuilder.like(criteriaBuilder.lower(root.get("subject")), likePattern);
                Predicate bodyLike = criteriaBuilder.like(criteriaBuilder.lower(root.get("body")), likePattern);
                predicates.add(criteriaBuilder.or(subjectLike, bodyLike));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
```
```java
// src/main/java/com/fetchmessagesapi/fma/security/AuthenticationFilterFMA.java