package com.chatserver.services.impl;

import com.chatserver.data.RandomChatRoomIdGenerator;
import com.chatserver.data.ChatMessageBuilder;
import com.chatserver.data.entities.ChatMessageStore;
import com.chatserver.data.repositories.ChatMessageStoreJAPRepository;
import com.chatserver.services.DefaultChatRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class DefaultChatRoomServiceImpl implements DefaultChatRoomService {
    @Autowired
    ChatMessageStoreJAPRepository chatMessageStoreJAPRepository;
    private final RandomChatRoomIdGenerator randomChatRoomIdGenerator;

    @Autowired
    public DefaultChatRoomServiceImpl(RandomChatRoomIdGenerator randomChatRoomIdGenerator) {
        this.randomChatRoomIdGenerator = randomChatRoomIdGenerator;
    }


    @Override
    public void sendMessage(String message, WebSocketSession session) {
        ChatMessageStore message1 = new ChatMessageBuilder.Builder()
                .message(message)
                .timestamp(LocalDateTime.now())
                .userName(Objects.requireNonNull(session.getPrincipal()).getName())
                .chatRoomId(randomChatRoomIdGenerator.getChatRoomId())
                .build();
        chatMessageStoreJAPRepository.save(message1);

    }

    @Override
    public List<ChatMessageStore> getMessagesByUserName(String username) {

        return chatMessageStoreJAPRepository.findByUsername(username);
    }

    @Override
    public List<ChatMessageStore> getAllMessages() {
        return chatMessageStoreJAPRepository.findAll();
    }

    @Override
    public void deleteMessage(Long id) {
      //delete chat messages
    }
}