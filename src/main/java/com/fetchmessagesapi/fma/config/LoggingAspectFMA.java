package com.fetchmessagesapi.fma.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * Aspect for logging execution of service and repository Spring components.
 * Fulfills the requirement to log start/end time of function calls.
 */
@Aspect
@Component
public class LoggingAspectFMA {
    private static final Logger log = LogManager.getLogger(LoggingAspectFMA.class);

    /**
     * Pointcut that matches all repositories, services, and controllers.
     */
    @Pointcut("within(@org.springframework.stereotype.Repository *)" +
            " || within(@org.springframework.stereotype.Service *)" +
            " || within(@org.springframework.web.bind.annotation.RestController *)")
    public void springBeanPointcut() {
        // Method is empty as this is just a Pointcut, the implementations are in the advices.
    }

    /**
     * Advice that logs methods execution time and parameters.
     * @param joinPoint join point for advice
     * @return result of the method execution
     * @throws Throwable if method throws an exception
     */
    @Around("springBeanPointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();

        log.debug("Enter: {}.{}() with argument[s] = {}", className, methodName, Arrays.toString(joinPoint.getArgs()));

        try {
            Object result = joinPoint.proceed();
            long endTime = System.currentTimeMillis();
            log.debug("Exit: {}.{}() with result = {}; Execution time = {} ms", className, methodName, result, (endTime - startTime));
            return result;
        } catch (IllegalArgumentException e) {
            log.error("Illegal argument: {} in {}.{}()", Arrays.toString(joinPoint.getArgs()),
                    className, methodName);
            throw e;
        }
    }
}
```
```java
// src/main/java/com/fetchmessagesapi/fma/config/SecurityConfigFMA.java