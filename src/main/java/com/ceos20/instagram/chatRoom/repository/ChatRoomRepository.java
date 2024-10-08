package com.ceos20.instagram.chatRoom.repository;

import com.ceos20.instagram.chatRoom.domain.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
}
