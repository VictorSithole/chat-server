package com.chatserver.data;

import com.chatserver.data.entities.ChatRoomMessageStore;

import java.time.LocalDateTime;

public class ChatRoomMessageBuilder {
    private ChatRoomMessageBuilder() {}
    public static class Builder {
        private String message;
        private LocalDateTime timestamp;
        private Long chatRoomId;
        private String userName;

        public Builder message(String message) {
            if (message == null || message.isEmpty()) {
                throw new IllegalArgumentException("Message cannot be null or empty");
            }
            this.message = message;
            return this;
        }

        public Builder timestamp(LocalDateTime timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public Builder chatRoomId(Long chatRoomId) {
            if (chatRoomId == null) {
                throw new IllegalArgumentException("Chat room ID cannot be null");
            }
            this.chatRoomId = chatRoomId;
            return this;
        }

        public Builder userName(String userName) {
            this.userName = userName;
            return this;
        }

        public ChatRoomMessageStore build() {
            if (message == null || message.isEmpty()) {
                throw new IllegalStateException("Message must be set");
            }
            if (chatRoomId == null) {
                throw new IllegalStateException("Chat room ID must be set");
            }
            ChatRoomMessageStore chatMessage = new ChatRoomMessageStore();
            chatMessage.setMessage(this.message);
            chatMessage.setTimestamp(this.timestamp);
            chatMessage.setChatRoomId(this.chatRoomId);
            chatMessage.setUsername(this.userName);
            return chatMessage;
        }
    }
}
