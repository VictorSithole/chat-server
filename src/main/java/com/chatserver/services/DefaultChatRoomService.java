package com.chatserver.services;

import com.chatserver.data.entities.ChatMessageStore;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;

public interface DefaultChatRoomService {

    void sendMessage(String message, WebSocketSession webSocketSession);
    List<ChatMessageStore> getMessagesByUserName(String userName);
    List<ChatMessageStore> getAllMessages();
    void deleteMessage(Long id);

}
