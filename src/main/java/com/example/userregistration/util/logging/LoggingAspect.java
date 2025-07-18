package com.example.userregistration.util.logging;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * AOP Aspect for logging method entry, exit, and execution time.
 * This provides cross-cutting logging functionality for performance monitoring.
 */
@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LogManager.getLogger(LoggingAspect.class);

    /**
     * Pointcut that matches all methods in the controller package.
     */
    @Pointcut("within(com.example.userregistration.controller..*)")
    public void controllerMethods() {}

    /**
     * Pointcut that matches all methods in the service package.
     */
    @Pointcut("within(com.example.userregistration.service..*)")
    public void serviceMethods() {}

    /**
     * Around advice that logs the execution of targeted methods.
     * Logs the start, end, and duration of each method call.
     *
     * @param joinPoint The join point for the advised method.
     * @return The result of the method execution.
     * @throws Throwable if the advised method throws an exception.
     */
    @Around("controllerMethods() || serviceMethods()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();

        logger.info("Enter: {}.{}()", className, methodName);

        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long endTime = System.currentTimeMillis();
        long timeTaken = endTime - startTime;

        logger.info("Exit: {}.{}() executed in {} ms", className, methodName, timeTaken);
        return result;
    }
}


// src/main/java/com/example/userregistration/util/logging/ExternalSystemLogAppender.java