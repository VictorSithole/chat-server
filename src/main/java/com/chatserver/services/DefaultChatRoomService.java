package com.chatserver.services;

import com.chatserver.data.entities.ChatRoomMessageStore;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;

public interface DefaultChatRoomService {

    void sendMessage(String message, WebSocketSession webSocketSession);
    List<ChatRoomMessageStore> getMessagesByUserName(String userName);
    List<ChatRoomMessageStore> getAllMessages();
    void deleteMessage(Long id);

}
