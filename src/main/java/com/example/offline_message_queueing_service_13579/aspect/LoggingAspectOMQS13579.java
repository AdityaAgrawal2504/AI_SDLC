package com.example.offline_message_queueing_service_13579.aspect;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import java.util.Arrays;
import java.util.stream.Collectors;

@Aspect
@Component
@Log4j2
public class LoggingAspectOMQS13579 {

    /**
     * Defines a pointcut that matches all public methods in the controller, service, and repository packages.
     */
    @Pointcut("within(com.example.offline_message_queueing_service_13579.controller..*) || " +
              "within(com.example.offline_message_queueing_service_13579.service..*) || " +
              "within(com.example.offline_message_queueing_service_13579.repository..*)")
    public void applicationPackagePointcut() {
        // Method is empty as this is just a Pointcut definition.
    }

    /**
     * Logs method entry, exit, execution time, and any exceptions thrown.
     */
    @Around("applicationPackagePointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        String className = joinPoint.getSignature().getDeclaringType().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        String args = Arrays.stream(joinPoint.getArgs())
                            .map(String::valueOf)
                            .collect(Collectors.joining(", "));

        log.debug("Enter: {}.{}() with argument[s] = {}", className, methodName, args);

        long startTime = System.currentTimeMillis();
        Object result;
        try {
            result = joinPoint.proceed();
            long endTime = System.currentTimeMillis();
            long timeTaken = endTime - startTime;
            log.debug("Exit: {}.{}() with result = {}. Execution time = {} ms", className, methodName, result, timeTaken);
            return result;
        } catch (IllegalArgumentException e) {
            log.error("Illegal argument: {} in {}.{}()", args, className, methodName);
            throw e;
        } catch (Throwable t) {
            long endTime = System.currentTimeMillis();
            long timeTaken = endTime - startTime;
            log.error("Exception in {}.{}() with cause = \'{}\' and exception = \'{}\'. Execution time = {} ms", className, methodName, t.getCause(), t.getMessage(), timeTaken);
            throw t;
        }
    }
}
```