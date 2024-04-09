package com.chatserver.config;

import com.chatserver.services.DefaultChatRoomHandler;
import com.chatserver.services.WebSocketHandShakeInterceptor;
import com.chatserver.services.impl.DefaultChatRoomServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketSecurityConfig implements WebSocketConfigurer {

    private final DefaultChatRoomServiceImpl chatRoomService;
    
    public WebSocketSecurityConfig(DefaultChatRoomServiceImpl chatRoomService) {
        this.chatRoomService = chatRoomService;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocketHandler(), "/websocket")
                .addInterceptors(new WebSocketHandShakeInterceptor());
    }
    @Bean
    public WebSocketHandler webSocketHandler() {
        return new DefaultChatRoomHandler(chatRoomService);
    }

}
