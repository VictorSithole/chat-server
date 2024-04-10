package com.chatserver.services.impl;

import com.chatserver.data.RandomChatRoomIdGenerator;
import com.chatserver.data.ChatRoomMessageBuilder;
import com.chatserver.data.entities.ChatRoomMessageStore;
import com.chatserver.data.repositories.ChatRoomMessageStoreJAPRepository;
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
    ChatRoomMessageStoreJAPRepository chatRoomMessageStoreJAPRepository;
    private final RandomChatRoomIdGenerator randomChatRoomIdGenerator;

    @Autowired
    public DefaultChatRoomServiceImpl(RandomChatRoomIdGenerator randomChatRoomIdGenerator) {
        this.randomChatRoomIdGenerator = randomChatRoomIdGenerator;
    }


    @Override
    public void sendMessage(String message, WebSocketSession session) {
        ChatRoomMessageStore message1 = new ChatRoomMessageBuilder.Builder()
                .message(message)
                .timestamp(LocalDateTime.now())
                .userName(Objects.requireNonNull(session.getPrincipal()).getName())
                .chatRoomId(randomChatRoomIdGenerator.getChatRoomId())
                .build();
        chatRoomMessageStoreJAPRepository.save(message1);

    }

    @Override
    public List<ChatRoomMessageStore> getMessagesByUserName(String username) {

        return chatRoomMessageStoreJAPRepository.findByUsername(username);
    }

    @Override
    public List<ChatRoomMessageStore> getAllMessages() {
        return chatRoomMessageStoreJAPRepository.findAll();
    }

    @Override
    public void deleteMessage(Long id) {
        chatRoomMessageStoreJAPRepository.deleteById(id);
    }
}
