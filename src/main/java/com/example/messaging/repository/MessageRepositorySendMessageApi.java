package com.example.messaging.repository;

import com.example.messaging.entity.MessageSendMessageApi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Spring Data JPA repository for the MessageSendMessageApi entity.
 */
@Repository
public interface MessageRepositorySendMessageApi extends JpaRepository<MessageSendMessageApi, UUID> {
}
```
```java