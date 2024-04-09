package com.chatserver.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.util.Base64;
import java.util.Map;

@Component
public class WebSocketHandShakeInterceptor implements HandshakeInterceptor {

    private static final Logger log = LoggerFactory.getLogger(WebSocketHandShakeInterceptor.class);

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
                                   Map<String, Object> attributes) throws Exception {
        HttpHeaders headers = request.getHeaders();

        // Check if the Authorization header is present
        if (headers.containsKey(HttpHeaders.AUTHORIZATION)) {
            // Get the value of the Authorization header
            String authHeader = headers.getFirst(HttpHeaders.AUTHORIZATION);
            String username = null;
            String password = null;
            if (authHeader != null) {
                // Extract the credentials from the Authorization header
                String credentials = authHeader.substring("Basic".length()).trim();

                // Decode the Base64-encoded credentials
                byte[] decodedCredentials = Base64.getDecoder().decode(credentials);
                String decodedCredentialsString = new String(decodedCredentials, StandardCharsets.UTF_8);
                
                String[] splitCredentials = decodedCredentialsString.split(":");
                username = splitCredentials[0];
                password = splitCredentials[1];
            }

            // Perform authentication logic
            if ("admin".equals(username) && "admin".equals(password)) {
                // Authentication successful
                attributes.put("username", username);
                return true;
            }
        }

        // Authentication failed
        return false;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
                               Exception ex) {
        String username = null;
        Principal principal = request.getPrincipal();
        if (principal != null) {
            username = principal.getName();
        }
        log.info("User authenticated successfully: {}", (username != null ? username : "unknown"));
    }
}
