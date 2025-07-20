package com.fetchmessagesapi.fma.repository;

import com.fetchmessagesapi.fma.model.MessageEntityFMA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Spring Data JPA repository for MessageEntityFMA.
 * Includes JpaSpecificationExecutor to support dynamic, criteria-based queries.
 */
@Repository
public interface MessageRepositoryFMA extends JpaRepository<MessageEntityFMA, UUID>, JpaSpecificationExecutor<MessageEntityFMA> {
}
```
```java
// src/main/java/com/fetchmessagesapi/fma/repository/MessageSpecificationFMA.java