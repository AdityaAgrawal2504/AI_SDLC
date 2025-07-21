package com.example.auth.lwc8765.controller;

import com.example.auth.lwc8765.dto.request.LoginCredentialsRequestLWC_8765;
import com.example.auth.lwc8765.dto.response.LoginInitiatedResponseLWC_8765;
import com.example.auth.lwc8765.exception.RateLimitExceptionLWC_8765;
import com.example.auth.lwc8765.service.IAuthenticationServiceLWC_8765;
import com.example.auth.lwc8765.util.ErrorCodeLWC_8765;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/auth")
public class AuthControllerLWC_8765 {

    private static final Logger log = LogManager.getLogger(AuthControllerLWC_8765.class);
    private final IAuthenticationServiceLWC_8765 authenticationService;
    private final Map<String, Bucket> cache = new ConcurrentHashMap<>();

    private final int capacity;
    private final int refillRate;
    private final int refillPeriodMinutes;

    public AuthControllerLWC_8765(IAuthenticationServiceLWC_8765 authenticationService,
                                @Value("${auth.lwc8765.ratelimit.capacity}") int capacity,
                                @Value("${auth.lwc8765.ratelimit.refill-rate}") int refillRate,
                                @Value("${auth.lwc8765.ratelimit.refill-period-minutes}") int refillPeriodMinutes) {
        this.authenticationService = authenticationService;
        this.capacity = capacity;
        this.refillRate = refillRate;
        this.refillPeriodMinutes = refillPeriodMinutes;
    }

    /**
     * Creates a new rate limiting bucket.
     */
    private Bucket createNewBucket() {
        Refill refill = Refill.intervally(refillRate, Duration.ofMinutes(refillPeriodMinutes));
        Bandwidth limit = Bandwidth.classic(capacity, refill);
        return Bucket.builder().addLimit(limit).build();
    }
    
    /**
     * Handles the login request, validating credentials and initiating OTP flow.
     */
    @PostMapping("/login")
    public ResponseEntity<LoginInitiatedResponseLWC_8765> login(
            @Valid @RequestBody LoginCredentialsRequestLWC_8765 request, HttpServletRequest httpServletRequest) {

        long startTime = System.currentTimeMillis();
        ThreadContext.put("requestId", java.util.UUID.randomUUID().toString());
        ThreadContext.put("endpoint", "/auth/login");
        log.info("Received login request for phone: {}", request.getPhoneNumber());

        String ip = httpServletRequest.getRemoteAddr();
        Bucket bucket = cache.computeIfAbsent(ip, k -> createNewBucket());

        if (!bucket.tryConsume(1)) {
            throw new RateLimitExceptionLWC_8765(
                ErrorCodeLWC_8765.RATE_LIMIT_EXCEEDED,
                "Too many login attempts. Please try again in " + refillPeriodMinutes + " minutes."
            );
        }

        LoginInitiatedResponseLWC_8765 response = authenticationService.initiateLogin(request);

        long endTime = System.currentTimeMillis();
        ThreadContext.put("executionTime", String.valueOf(endTime - startTime));
        log.info("Login request processed successfully.");
        ThreadContext.clearMap();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
```
```java
// src/main/java/com/example/auth/lwc8765/AuthLwc8765Application.java