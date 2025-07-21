package com.example.conversations.api.logging;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * Aspect for logging the execution of service and controller methods.
 * Provides structured logs including execution time.
 */
@Aspect
@Component
@Log4j2
public class LoggingAspectGCAPI_9876 {

    /**
     * Defines a pointcut for all methods in the controller package.
     */
    @Pointcut("within(com.example.conversations.api.controller..*)")
    public void controllerPointcut() {
    }

    /**
     * Defines a pointcut for all methods in the service package.
     */
    @Pointcut("within(com.example.conversations.api.service..*)")
    public void servicePointcut() {
    }

    /**
     * Logs method entry, exit, and execution time around the targeted methods.
     * @param joinPoint The proceeding join point.
     * @return The result of the method execution.
     * @throws Throwable If the method execution fails.
     */
    @Around("controllerPointcut() || servicePointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();

        log.info("Enter: {}.{}()", className, methodName);

        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long endTime = System.currentTimeMillis();

        log.info("Exit: {}.{}(); Execution Time: {}ms", className, methodName, (endTime - startTime));

        return result;
    }
}
```
```java
// src/main/java/com/example/conversations/api/model/ConversationSortByGCAPI_9876.java