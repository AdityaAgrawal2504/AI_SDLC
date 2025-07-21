package com.example.offline_message_queueing_service_13579.scheduler;

import com.example.offline_message_queueing_service_13579.service.MessageQueueServiceOMQS13579;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Log4j2
public class ScheduledTasksOMQS13579 {

    private final MessageQueueServiceOMQS13579 messageQueueService;

    /**
     * Periodically purges messages older than 7 days from the queue.
     * Runs once every day at 3:00 AM server time.
     */
    @Scheduled(cron = "0 0 3 * * *")
    public void reportCurrentTime() {
        log.info("Starting scheduled job to purge old messages.");
        messageQueueService.purgeOldMessages();
        log.info("Finished scheduled job to purge old messages.");
    }
}
```
```java
// src/main/java/com/example/offline_message_queueing_service_13579/aspect/LoggingAspectOMQS13579.java