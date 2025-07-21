package com.example.fetchconversationsapi1.aspect;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * Aspect for implementing structured, timed logging for key application layers.
 * <!--
 * mermaid
 * sequenceDiagram
 *   Client->>+Controller: API Request
 *   Controller->>+LoggingAspect: Before Execution
 *   LoggingAspect->>LoggingAspect: Log Start (method, args)
 *   LoggingAspect->>+Service: Method Call
 *   Service->>+LoggingAspect: Before Execution
 *   LoggingAspect->>LoggingAspect: Log Start (method, args)
 *   LoggingAspect->>+Repository: Method Call
 *   Repository-->>-LoggingAspect: Return
 *   LoggingAspect->>LoggingAspect: Log End (method, duration)
 *   Service-->>-LoggingAspect: Return
 *   LoggingAspect->>LoggingAspect: Log End (method, duration)
 *   Controller-->>-LoggingAspect: Return
 *   LoggingAspect->>LoggingAspect: Log End (method, duration)
 *   LoggingAspect-->>-Client: API Response
 * -->
 */
@Aspect
@Component
public class LoggingAspect_FCAPI_1 {

    private static final Logger log = LogManager.getLogger(LoggingAspect_FCAPI_1.class);

    /**
     * Pointcut that matches all methods in the controller, service, and repository packages.
     */
    @Pointcut("within(com.example.fetchconversationsapi1.controller..*) || " +
              "within(com.example.fetchconversationsapi1.service..*) || " +
              "within(com.example.fetchconversationsapi1.repository..*)")
    public void applicationPackagePointcut() {
        // Method is empty as this is just a Pointcut definition.
    }

    /**
     * Around advice that logs method entry, exit, execution time, and errors.
     */
    @Around("applicationPackagePointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        String className = joinPoint.getSignature().getDeclaringType().getSimpleName();
        String methodName = joinPoint.getSignature().getName();

        MDC.put("className", className);
        MDC.put("methodName", methodName);

        log.info("Enter: {}.{}() with argument[s] = {}", className, methodName, Arrays.toString(joinPoint.getArgs()));

        long startTime = System.currentTimeMillis();
        Object result;
        try {
            result = joinPoint.proceed();
            return result;
        } catch (IllegalArgumentException e) {
            log.error("Illegal argument: {} in {}.{}()", Arrays.toString(joinPoint.getArgs()), className, methodName);
            throw e;
        } finally {
            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;
            MDC.put("durationMs", String.valueOf(duration));
            log.info("Exit: {}.{}() with duration = {}ms", className, methodName, duration);
            MDC.clear();
        }
    }
}
```
```java
// src/main/resources/data.sql
-- Mock data for H2 database
-- Create Users
INSERT INTO app_user (id, display_name, avatar_url) VALUES
('f47ac10b-58cc-4372-a567-0e02b2c3d479', 'Authenticated User', 'https://example.com/avatar1.png'),
('a1b2c3d4-e5f6-7890-1234-567890abcdef', 'Jane Doe', 'https://example.com/avatar2.png'),
('b2c3d4e5-f6a7-8901-2345-67890abcdef0', 'John Smith', 'https://example.com/avatar3.png');

-- Create Conversations
INSERT INTO conversation (id, title, created_at, updated_at) VALUES
('c1000001-0000-0000-0000-000000000001', 'Project Alpha', '2023-10-27T10:00:00Z', '2023-10-27T12:05:00Z'),
('c1000002-0000-0000-0000-000000000002', 'Weekend Plans', '2023-10-26T15:30:00Z', '2023-10-26T16:00:00Z'),
('c1000003-0000-0000-0000-000000000003', 'Lunch', '2023-10-25T09:00:00Z', '2023-10-25T09:15:00Z');

-- Create Messages and link last_message to conversation
INSERT INTO message (id, conversation_id, sender_id, content, created_at) VALUES
('m1000001-0000-0000-0000-000000000001', 'c1000001-0000-0000-0000-000000000001', 'a1b2c3d4-e5f6-7890-1234-567890abcdef', 'Hey team, the spec for the new feature is ready.', '2023-10-27T12:05:00Z');
UPDATE conversation SET last_message_id = 'm1000001-0000-0000-0000-000000000001' WHERE id = 'c1000001-0000-0000-0000-000000000001';

INSERT INTO message (id, conversation_id, sender_id, content, created_at) VALUES
('m1000002-0000-0000-0000-000000000002', 'c1000002-0000-0000-0000-000000000002', 'b2c3d4e5-f6a7-8901-2345-67890abcdef0', 'Are we still on for the hike?', '2023-10-26T16:00:00Z');
UPDATE conversation SET last_message_id = 'm1000002-0000-0000-0000-000000000002' WHERE id = 'c1000002-0000-0000-0000-000000000002';

INSERT INTO message (id, conversation_id, sender_id, content, created_at) VALUES
('m1000003-0000-0000-0000-000000000003', 'c1000003-0000-0000-0000-000000000003', 'a1b2c3d4-e5f6-7890-1234-567890abcdef', 'Pizza today?', '2023-10-25T09:15:00Z');
UPDATE conversation SET last_message_id = 'm1000003-0000-0000-0000-000000000003' WHERE id = 'c1000003-0000-0000-0000-000000000003';

-- Add another message to conversation 1 to test message search
INSERT INTO message (id, conversation_id, sender_id, content, created_at) VALUES
('m1000004-0000-0000-0000-000000000004', 'c1000001-0000-0000-0000-000000000001', 'f47ac10b-58cc-4372-a567-0e02b2c3d479', 'Great, I will review the new search functionality.', '2023-10-27T11:00:00Z');


-- Create Participants and set seen status
-- Conversation 1: Auth User (unseen) and Jane Doe
INSERT INTO conversation_participant (id, conversation_id, user_id, last_seen_message_id) VALUES
(random_uuid(), 'c1000001-0000-0000-0000-000000000001', 'f47ac10b-58cc-4372-a567-0e02b2c3d479', null), -- Unseen
(random_uuid(), 'c1000001-0000-0000-0000-000000000001', 'a1b2c3d4-e5f6-7890-1234-567890abcdef', 'm1000001-0000-0000-0000-000000000001'); -- Seen

-- Conversation 2: Auth User (seen) and John Smith
INSERT INTO conversation_participant (id, conversation_id, user_id, last_seen_message_id) VALUES
(random_uuid(), 'c1000002-0000-0000-0000-000000000002', 'f47ac10b-58cc-4372-a567-0e02b2c3d479', 'm1000002-0000-0000-0000-000000000002'), -- Seen
(random_uuid(), 'c1000002-0000-0000-0000-000000000002', 'b2c3d4e5-f6a7-8901-2345-67890abcdef0', 'm1000002-0000-0000-0000-000000000002'); -- Seen

-- Conversation 3: Auth User (unseen) and Jane Doe
INSERT INTO conversation_participant (id, conversation_id, user_id, last_seen_message_id) VALUES
(random_uuid(), 'c1000003-0000-0000-0000-000000000003', 'f47ac10b-58cc-4372-a567-0e02b2c3d479', null), -- Unseen
(random_uuid(), 'c1000003-0000-0000-0000-000000000003', 'a1b2c3d4-e5f6-7890-1234-567890abcdef', 'm1000003-0000-0000-0000-000000000003'); -- Seen

```