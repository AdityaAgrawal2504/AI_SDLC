package com.chatapp.chatservice.logging;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Log4j2
public class LoggingAspect_C1A7 {

    /**
     * Defines a pointcut for all public methods in the service layer.
     */
    @Pointcut("within(com.chatapp.chatservice.service..*)")
    public void serviceLayerPointcut() {
    }
    
    /**
     * Defines a pointcut for the main gRPC service implementation class.
     */
    @Pointcut("within(com.chatapp.chatservice.grpc.ChatServiceImpl_C1A7)")
    public void grpcServicePointcut() {
    }


    /**
     * AOP advice that logs the execution time of methods in the service and gRPC layers.
     */
    @Around("serviceLayerPointcut() || grpcServicePointcut()")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        log.debug("START: {}.{}", className, methodName);

        Object result;
        try {
            result = joinPoint.proceed();
        } finally {
            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;
            log.debug("END: {}.{} | DURATION: {}ms", className, methodName, duration);
        }

        return result;
    }
}
```
```java
// src/main/java/com/chatapp/chatservice/repository/ChatRepository_C1A7.java