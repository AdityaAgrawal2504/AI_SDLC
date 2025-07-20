package com.example.chat.grpc;

import "google/protobuf/timestamp.proto";

option java_multiple_files = true;
option java_package = "com.example.chat.grpc.spec";
option java_outer_classname = "ChatRealtimeChatStreamGRPCProto";

// Service definition
service ChatServiceGRPC {
  // Establishes a bi-directional gRPC stream for real-time chat.
  rpc LiveChat (stream ChatStreamRequestGRPC) returns (stream ChatStreamResponseGRPC);
}

// ============== Client to Server Messages ==============

message ChatStreamRequestGRPC {
  oneof request_oneof {
    JoinRequestGRPC join_request = 1;
    SendMessageGRPC send_message = 2;
    TypingEventGRPC typing_event = 3;
  }
}

message JoinRequestGRPC {
  string user_id = 1;
  string channel_id = 2;
  string session_token = 3;
}

message SendMessageGRPC {
  string client_message_id = 1;
  string content = 2;
  map<string, string> metadata = 3;
}

message TypingEventGRPC {
  bool is_typing = 1;
}

// ============== Server to Client Messages ==============

message ChatStreamResponseGRPC {
  oneof response_oneof {
    ConnectionAckGRPC connection_ack = 1;
    ChatMessageGRPC new_message = 2;
    MessageDeliveryReceiptGRPC message_delivery_receipt = 3;
    TypingNotificationGRPC typing_notification = 4;
    UserEventGRPC user_event = 5;
    StreamErrorGRPC error_message = 6;
  }
}

message ConnectionAckGRPC {
  enum Status {
    UNDEFINED = 0;
    SUCCESS = 1;
    FAILURE = 2;
  }
  Status status = 1;
  string message = 2;
  repeated ChatMessageGRPC channel_history = 3;
}

message ChatMessageGRPC {
  string message_id = 1;
  string channel_id = 2;
  string sender_id = 3;
  string sender_display_name = 4;
  string content = 5;
  google.protobuf.Timestamp timestamp = 6;
  map<string, string> metadata = 7;
}

message MessageDeliveryReceiptGRPC {
  enum Status {
    UNKNOWN = 0;
    DELIVERED = 1;
    FAILED = 2;
  }
  string client_message_id = 1;
  string server_message_id = 2;
  google.protobuf.Timestamp timestamp = 3;
  Status status = 4;
}

message TypingNotificationGRPC {
  string user_id = 1;
  string user_display_name = 2;
  bool is_typing = 3;
}

message UserEventGRPC {
  enum EventType {
    NONE = 0;
    JOINED = 1;
    LEFT = 2;
  }
  string user_id = 1;
  string user_display_name = 2;
  EventType event_type = 3;
  google.protobuf.Timestamp timestamp = 4;
}

message StreamErrorGRPC {
  int32 error_code = 1;
  string error_message = 2;
}
```
src/main/resources/application-realtimeChatStreamGRPC.properties
```properties
# Spring Boot application name
spring.application.name=realtime-chat-stream-grpc

# gRPC Server Configuration
grpc.server.port=9090
grpc.server.enable-keep-alive=true
grpc.server.keep-alive-time=60s
grpc.server.keep-alive-timeout=20s
grpc.server.permit-keep-alive-without-calls=true

# Logging configuration file
logging.config=classpath:log4j2-realtimeChatStreamGRPC.xml

# Custom Application Properties
chat.message.max-length=4096
```
src/main/resources/log4j2-realtimeChatStreamGRPC.xml
```xml