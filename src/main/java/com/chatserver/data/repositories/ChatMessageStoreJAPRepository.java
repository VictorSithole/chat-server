package com.chatserver.data.repositories;

import com.chatserver.data.entities.ChatMessageStore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageStoreJAPRepository extends JpaRepository<ChatMessageStore, Long> {
    List<ChatMessageStore> findByUsername(String username);
}
