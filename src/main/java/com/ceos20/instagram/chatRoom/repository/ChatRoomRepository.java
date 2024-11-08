package com.ceos20.instagram.chatRoom.repository;

import com.ceos20.instagram.chatRoom.domain.ChatRoom;
import com.ceos20.instagram.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    @Query("SELECT c.id FROM ChatRoom c WHERE c.member1 = :member OR c.member2 = :member")
    List<Long> findByMember(Member member);
}
