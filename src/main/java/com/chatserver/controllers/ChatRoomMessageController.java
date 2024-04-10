package com.chatserver.controllers;

import com.chatserver.data.entities.ChatRoomMessageStore;
import com.chatserver.services.impl.DefaultChatRoomServiceImpl;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;

@RestController
@RequestMapping("/api/chat-room/messages")
public class ChatRoomMessageController {

    private DefaultChatRoomServiceImpl webSocketService;

    public ChatRoomMessageController(DefaultChatRoomServiceImpl webSocketService) {
        this.webSocketService = webSocketService;
    }

    @PostMapping
    public ResponseEntity<String> sendMessage(@RequestBody String message, HttpSession httpSession) {
        //Process the message and send it via WebSocket
        WebSocketSession session = (WebSocketSession) httpSession.getAttribute("websocketSession");
        if (session == null) {
            throw new RuntimeException("Session is not valid can not send message");
        }
        webSocketService.sendMessage(message, session);
        return ResponseEntity.ok("Message sent successfully");
    }

    @GetMapping
    public ResponseEntity<List<ChatRoomMessageStore>> getMessages() {
        //Retrieve chatroom messages
        List<ChatRoomMessageStore> messages = webSocketService.getAllMessages();
        return ResponseEntity.ok(messages);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMessage(@PathVariable Long id) {
        // Delete the message with the provided ID
        webSocketService.deleteMessage(id);
        return ResponseEntity.ok("Message deleted successfully");
    }
}
