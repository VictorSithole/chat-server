package com.chatserver.services;

import com.chatserver.data.entities.ChatRoomMessageStore;
import com.chatserver.services.impl.DefaultChatRoomServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.*;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class DefaultChatRoomHandler implements WebSocketHandler {

    private final DefaultChatRoomServiceImpl chatRoomService;

    private static final Logger log = LoggerFactory.getLogger(DefaultChatRoomHandler.class);

    public DefaultChatRoomHandler(DefaultChatRoomServiceImpl chatRoomService) {
        this.chatRoomService = chatRoomService;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws IOException {
        String username = Objects.requireNonNull(session.getPrincipal()).getName();
        session.sendMessage(new TextMessage("You have successfully connected to the Server Chat Room: " + username.toUpperCase()));

        List<ChatRoomMessageStore> chatRoomMessageStores = chatRoomService.getMessagesByUserName(username.toUpperCase());

        if (chatRoomMessageStores == null) {
            session.sendMessage(new TextMessage("No messages found for user: " + username.toUpperCase()));
        } else {
            for (ChatRoomMessageStore chatRoomMessageStore : chatRoomMessageStores) {
                session.sendMessage(new TextMessage(chatRoomMessageStore.getMessage()));
            }
        }
        log.info("Connected to chat: {}", session.getId());
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {

        if (message instanceof TextMessage textMessage) {
            String chatMessage = textMessage.getPayload();
            chatRoomService.sendMessage(chatMessage, session);
            log.info("Message sent to the server from the user {}: {}", session.getId(), chatMessage);
            session.sendMessage(new TextMessage("Message received by the chat server: " + chatMessage));
        } else {
            log.warn("Received unsupported message type from user {}", session.getId());
            session.close(CloseStatus.NOT_ACCEPTABLE.withReason("Unsupported message type"));
        }
    }
    
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws IOException {
        String username = Objects.requireNonNull(session.getPrincipal()).getName();
        session.sendMessage(new TextMessage("Session terminated due to an error: " + username.toUpperCase()));
        log.error("Error encountered for user {}: {}", session.getId(), exception.getMessage());
    }
    
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) {
        log.info("User terminated chat: {} with status: {}", session.getId(), closeStatus.getCode());
    }


    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
