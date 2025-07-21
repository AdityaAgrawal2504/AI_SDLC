package com.example.fetchconversationsapi1.repository;

import com.example.fetchconversationsapi1.model.ConversationEntity_FCAPI_1;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Spring Data JPA repository for Conversation entities.
 * Includes JpaSpecificationExecutor for dynamic query building.
 */
@Repository
public interface ConversationRepository_FCAPI_1 extends JpaRepository<ConversationEntity_FCAPI_1, UUID>, JpaSpecificationExecutor<ConversationEntity_FCAPI_1> {
}
```
```java
// src/main/java/com/example/fetchconversationsapi1/repository/ConversationSpec_FCAPI_1.java