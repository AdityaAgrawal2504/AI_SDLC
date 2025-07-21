package com.chatapp.chatservice.grpc;

import com.chatapp.chatservice.domain.UserContext_C1A7;
import com.chatapp.chatservice.enums.StreamErrorCode_C1A7;
import com.chatapp.chatservice.exception.StreamErrorException_C1A7;
import com.chatapp.chatservice.service.ConnectionManager_C1A7;
import com.chatapp.chatservice.service.MessageService_C1A7;
import com.chatapp.chatservice.util.RequestValidator_C1A7;
import com.chatapp.chatservice.util.TimestampConverter_C1A7;
import com.google.protobuf.Timestamp;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.devh.boot.grpc.server.service.GrpcService;
import java.time.Instant;
import java.util.UUID;

/*
 * mermaid
 * sequenceDiagram
 *   participant Client
 *   participant ChatServiceImpl_C1A7
 *   participant ConnectionManager_C1A7
 *   participant MessageService_C1A7
 *
 *   Client->>ChatServiceImpl_C1A7: connectStream()
 *   Note over ChatServiceImpl_C1A7: Auth handled by Interceptor
 *   ChatServiceImpl_C1A7->>ConnectionManager_C1A7: register(userId, responseObserver)
 *   ChatServiceImpl_C1A7-->>Client: onNext(WelcomeEvent)
 *
 *   Client->>ChatServiceImpl_C1A7: onNext(ClientToServerMessage:SendMessage)
 *   ChatServiceImpl_C1A7->>MessageService_C1A7: handleSendMessage(request)
 *   MessageService_C1A7-->>ChatServiceImpl_C1A7: returns
 *   Note over ChatServiceImpl_C1A7: Broadcasts NewMessageEvent via ConnectionManager
 *
 *   Client->>ChatServiceImpl_C1A7: onNext(ClientToServerMessage:UpdateStatus)
 *   ChatServiceImpl_C1A7->>MessageService_C1A7: handleUpdateStatus(request)
 *   MessageService_C1A7-->>ChatServiceImpl_C1A7: returns
 *   Note over ChatServiceImpl_C1A7: Broadcasts MessageStatusUpdateEvent via ConnectionManager
 *
 *   Client->>ChatServiceImpl_C1A7: onCompleted()
 *   ChatServiceImpl_C1A7->>ConnectionManager_C1A7: unregister(userId)
 */
@Log4j2
@GrpcService
@RequiredArgsConstructor
public class ChatServiceImpl_C1A7 extends ChatServiceGrpc.ChatServiceImplBase {

    private final ConnectionManager_C1A7 connectionManager;
    private final MessageService_C1A7 messageService;

    /**
     * Handles the bi-directional gRPC stream for chat connections.
     */
    @Override
    public StreamObserver<ClientToServerMessage> connectStream(StreamObserver<ServerToClientMessage> responseObserver) {
        String userId = UserContext_C1A7.KEY.get().getUserId();
        String sessionId = UUID.randomUUID().toString();
        log.info("Stream connection initiated. userId={}, sessionId={}", userId, sessionId);

        connectionManager.register(userId, sessionId, responseObserver);
        sendWelcomeEvent(responseObserver, sessionId);

        return new StreamObserver<>() {
            /**
             * Handles incoming messages from the client.
             */
            @Override
            public void onNext(ClientToServerMessage request) {
                try {
                    switch (request.getPayloadCase()) {
                        case SEND_MESSAGE -> handleSendMessage(request.getSendMessage(), userId);
                        case UPDATE_STATUS -> handleUpdateStatus(request.getUpdateStatus(), userId);
                        case PAYLOAD_NOT_SET -> throw new StreamErrorException_C1A7(
                                StreamErrorCode_C1A7.MALFORMED_REQUEST, "Payload not set.", null);
                    }
                } catch (StreamErrorException_C1A7 e) {
                    log.warn("Stream error for user {}: {}", userId, e.getMessage());
                    sendStreamErrorEvent(responseObserver, e);
                } catch (StatusRuntimeException e) {
                    log.error("gRPC Status exception for user {}: {}", userId, e.getStatus(), e);
                    responseObserver.onError(e);
                    connectionManager.unregister(userId, sessionId);
                } catch (Exception e) {
                    log.error("Unhandled exception for user {}: {}", userId, e.getMessage(), e);
                    responseObserver.onError(Status.INTERNAL.withDescription("An unexpected server error occurred.").asRuntimeException());
                    connectionManager.unregister(userId, sessionId);
                }
            }

            /**
             * Handles stream errors from the client side.
             */
            @Override
            public void onError(Throwable t) {
                log.error("Stream error from client {}: {}", userId, t.getMessage());
                connectionManager.unregister(userId, sessionId);
            }

            /**
             * Handles the completion of the stream from the client side.
             */
            @Override
            public void onCompleted() {
                log.info("Stream completed by client. userId={}, sessionId={}", userId, sessionId);
                connectionManager.unregister(userId, sessionId);
                responseObserver.onCompleted();
            }
        };
    }

    /**
     * Processes a SendMessageRequest from the client.
     */
    private void handleSendMessage(SendMessageRequest request, String senderId) {
        RequestValidator_C1A7.validate(request);
        messageService.processNewMessage(request, senderId);
    }

    /**
     * Processes an UpdateStatusRequest from the client.
     */
    private void handleUpdateStatus(UpdateStatusRequest request, String updaterId) {
        RequestValidator_C1A7.validate(request);
        messageService.processStatusUpdate(request, updaterId);
    }

    /**
     * Sends a welcome event to the newly connected client.
     */
    private void sendWelcomeEvent(StreamObserver<ServerToClientMessage> responseObserver, String sessionId) {
        Timestamp serverTimestamp = TimestampConverter_C1A7.fromInstant(Instant.now());
        WelcomeEvent welcomeEvent = WelcomeEvent.newBuilder()
                .setSessionId(sessionId)
                .setServerTimestamp(serverTimestamp)
                .build();
        ServerToClientMessage response = ServerToClientMessage.newBuilder().setWelcomeEvent(welcomeEvent).build();
        responseObserver.onNext(response);
    }

    /**
     * Sends a non-fatal error event to the client over the stream.
     */
    private void sendStreamErrorEvent(StreamObserver<ServerToClientMessage> responseObserver, StreamErrorException_C1A7 e) {
        StreamErrorEvent.Builder errorEventBuilder = StreamErrorEvent.newBuilder()
                .setErrorCode(e.getErrorCode().name())
                .setErrorMessage(e.getMessage());

        if (e.getOriginalClientMessageId() != null) {
            errorEventBuilder.setOriginalClientMessageId(e.getOriginalClientMessageId());
        }

        ServerToClientMessage response = ServerToClientMessage.newBuilder().setStreamErrorEvent(errorEventBuilder.build()).build();
        responseObserver.onNext(response);
    }
}
```
```java
// src/main/java/com/chatapp/chatservice/logging/EventHubAppender_C1A7.java