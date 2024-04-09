package com.chatserver.data;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class RandomChatRoomIdGenerator {
    private final Long chatRoomId;

    public RandomChatRoomIdGenerator() {
        this.chatRoomId = generateRandomId();
    }

    private Random random = new Random();

    private Long generateRandomId() {
        return (long) random.nextInt(9000);
    }
    public Long getChatRoomId() {
        return chatRoomId;
    }
}
