package com.chatserver.services;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.chatserver.data.entities.ChatRoomMessageStore;
import com.chatserver.data.repositories.ChatRoomMessageStoreJAPRepository;
import com.chatserver.enums.MessageType;
import com.chatserver.services.impl.DefaultChatRoomServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class DefaultChatRoomServiceImplTests {

    @Mock
    private ChatRoomMessageStoreJAPRepository chatRoomMessageJAPRepository;

    @InjectMocks
    private DefaultChatRoomServiceImpl chatRoomService;

    @BeforeEach
    void setUp() {
        // Mock behavior for chatRoomId
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getMessagesByUserName_shouldReturnMessages() {
        // Arrange
        String username = "username";
        List<ChatRoomMessageStore> messages = new ArrayList<>();
        messages.add(new ChatRoomMessageStore(1L, MessageType.CHAT, "Hi server", "Hello client", LocalDateTime.now(), "username", 1L));

        when(chatRoomMessageJAPRepository.findByUsername(username)).thenReturn(messages);

        // Act
        List<ChatRoomMessageStore> result = chatRoomService.getMessagesByUserName(username);

        // Assert
        assertEquals(messages, result);
    }

    @Test
    void getAllMessages_shouldReturnAllMessages() {
        // Arrange
        List<ChatRoomMessageStore> messages = new ArrayList<>();
        messages.add(new ChatRoomMessageStore(1L, MessageType.CHAT, "Hi server", "Hi", LocalDateTime.now(), "username", 1L));
        messages.add(new ChatRoomMessageStore(1L,MessageType.CHAT,  "Hi server", "Hi", LocalDateTime.now(), "username", 1L));
        when(chatRoomMessageJAPRepository.findAll()).thenReturn(messages);

        // Act
        List<ChatRoomMessageStore> result = chatRoomService.getAllMessages();

        // Assert
        assertEquals(messages, result);
    }

    @Test
    void deleteMessage_shouldDeleteMessage() {
        // Arrange
        Long messageId = 1L;
        doNothing().when(chatRoomMessageJAPRepository).deleteById(messageId);

        // Act
        chatRoomService.deleteMessage(messageId);

        // Assert
        verify(chatRoomMessageJAPRepository).deleteById(messageId);
    }
}

