package com.chatapp.chatservice.grpc;

import "google/protobuf/timestamp.proto";

option java_multiple_files = true;
option java_package = "com.chatapp.chatservice.grpc";
option java_outer_classname = "ChatProto_C1A7";

// The main service for real-time messaging.
service ChatService {
  // Establishes a bi-directional stream for real-time communication.
  // The client must send a valid authentication token in the gRPC metadata.
  rpc ConnectStream(stream ClientToServerMessage) returns (stream ServerToClientMessage);
}

// Represents the delivery status of a message.
enum MessageStatus {
  STATUS_UNSPECIFIED = 0;
  SENT = 1;
  DELIVERED = 2;
  SEEN = 3;
  FAILED = 4;
}

// A wrapper message sent from the client to the server over the stream.
message ClientToServerMessage {
  oneof payload {
    SendMessageRequest send_message = 1;
    UpdateStatusRequest update_status = 2;
  }
}

// A wrapper message sent from the server to the client over the stream.
message ServerToClientMessage {
  oneof payload {
    WelcomeEvent welcome_event = 1;
    NewMessageEvent new_message_event = 2;
    MessageStatusUpdateEvent message_status_update_event = 3;
    StreamErrorEvent stream_error_event = 4;
  }
}

// Payload for sending a new message.
message SendMessageRequest {
  // The unique identifier for the chat session.
  string chat_id = 1;
  // A client-generated unique ID (e.g., UUIDv4) for idempotency.
  string client_message_id = 2;
  // The text content of the message.
  string content = 3;
}

// Payload for updating the status of a message (e.g., marking it as SEEN).
message UpdateStatusRequest {
  // The unique identifier for the chat session.
  string chat_id = 1;
  // The server-generated unique identifier of the message to update.
  string message_id = 2;
  // The new status of the message. Only 'SEEN' is a valid client-initiated update.
  MessageStatus status = 3;
}

// Sent by the server upon successful connection and authentication.
message WelcomeEvent {
  // A unique identifier for this specific stream session.
  string session_id = 1;
  // The server time when the connection was established.
  google.protobuf.Timestamp server_timestamp = 2;
}

// Event sent to clients when a new message is posted in a chat they are connected to.
message NewMessageEvent {
  // The unique server-generated identifier for the message.
  string message_id = 1;
  // The identifier of the chat this message belongs to.
  string chat_id = 2;
  // The user ID of the message sender.
  string sender_id = 3;
  // The content of the message.
  string content = 4;
  // The server timestamp when the message was created.
  google.protobuf.Timestamp created_at = 5;
  // The original client-generated ID, used for matching sent messages with server events.
  string client_message_id = 6;
}

// Event sent to clients when a message's status changes (e.g., delivered, seen).
message MessageStatusUpdateEvent {
  // The ID of the message whose status was updated.
  string message_id = 1;
  // The ID of the chat this message belongs to.
  string chat_id = 2;
  // The new status of the message.
  MessageStatus status = 3;
  // The server timestamp of the status update.
  google.protobuf.Timestamp updated_at = 4;
  // The ID of the user who triggered the status update (e.g., the user who saw the message).
  string updated_by_user_id = 5;
}

// A non-fatal error sent over the stream that does not terminate the connection.
message StreamErrorEvent {
  // A machine-readable error code (e.g., 'MESSAGE_SEND_FAILED_BLOCKED').
  string error_code = 1;
  // A human-readable description of the error.
  string error_message = 2;
  // If the error relates to a specific client message, this is its ID.
  string original_client_message_id = 3;
}
```
```yaml
# src/main/resources/application.yml
grpc:
  server:
    port: 9090
    security:
      enabled: false # TLS is disabled for this example. In production, this should be true.

spring:
  application:
    name: chatservice-c1a7
  main:
    banner-mode: "off"

# Custom property for selecting log destination
logging:
  destination: "FILE" # Can be FILE, KAFKA, or EVENT_HUB
```
```xml
<!-- src/main/resources/log4j2.xml -->