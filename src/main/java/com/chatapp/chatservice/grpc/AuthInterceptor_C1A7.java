package com.chatapp.chatservice.grpc;

import com.chatapp.chatservice.domain.UserContext_C1A7;
import com.chatapp.chatservice.service.AuthService_C1A7;
import io.grpc.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.devh.boot.grpc.server.interceptor.GrpcGlobalServerInterceptor;

import java.util.Optional;

@Log4j2
@GrpcGlobalServerInterceptor
@RequiredArgsConstructor
public class AuthInterceptor_C1A7 implements ServerInterceptor {

    private final AuthService_C1A7 authService;
    public static final Metadata.Key<String> AUTH_HEADER_KEY = Metadata.Key.of("authorization", Metadata.ASCII_STRING_MARSHALLER);

    /**
     * Intercepts incoming gRPC calls to perform authentication.
     */
    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(
            ServerCall<ReqT, RespT> call,
            Metadata headers,
            ServerCallHandler<ReqT, RespT> next) {

        String authHeader = headers.get(AUTH_HEADER_KEY);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            log.warn("Authentication token is missing or malformed. Closing call.");
            call.close(Status.UNAUTHENTICATED.withDescription("Authorization token is missing or malformed."), new Metadata());
            return new ServerCall.Listener<>() {};
        }

        String token = authHeader.substring(7);
        Optional<String> userIdOptional = authService.validateTokenAndGetUserId(token);

        if (userIdOptional.isEmpty()) {
            log.warn("Invalid authentication token. Closing call.");
            call.close(Status.UNAUTHENTICATED.withDescription("Invalid authentication token."), new Metadata());
            return new ServerCall.Listener<>() {};
        }

        String userId = userIdOptional.get();
        UserContext_C1A7 userContext = new UserContext_C1A7(userId);
        Context context = Context.current().withValue(UserContext_C1A7.KEY, userContext);
        log.info("User authenticated successfully: userId={}", userId);

        return Contexts.interceptCall(context, call, headers, next);
    }
}
```
```java
// src/main/java/com/chatapp/chatservice/grpc/ChatServiceImpl_C1A7.java