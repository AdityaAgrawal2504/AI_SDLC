package com.example.offline_message_queueing_service_13579.service;

import com.example.offline_message_queueing_service_13579.dto.EnqueueResponseDtoOMQS13579;
import com.example.offline_message_queueing_service_13579.dto.MessageDtoOMQS13579;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public interface MessageQueueServiceOMQS13579 {
    EnqueueResponseDtoOMQS13579 enqueueMessage(UUID userId, MessageDtoOMQS13579 messageDto);
    Map<UUID, MessageDtoOMQS13579> dequeueMessages(UUID userId, int limit);
    void acknowledgeMessages(UUID userId, Set<UUID> messageIds);
    void purgeOldMessages();
}
```
```java
// src/main/java/com/example/offline_message_queueing_service_13579/service/MessageQueueServiceImplOMQS13579.java