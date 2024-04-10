package com.chatserver.data.repositories;

import com.chatserver.data.entities.ChatRoomMessageStore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRoomMessageStoreJAPRepository extends JpaRepository<ChatRoomMessageStore, Long> {
    List<ChatRoomMessageStore> findByUsername(String username);
}
