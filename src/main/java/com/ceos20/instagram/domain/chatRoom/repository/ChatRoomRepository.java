package com.ceos20.instagram.domain.chatRoom.repository;

import com.ceos20.instagram.domain.chatRoom.domain.ChatRoom;
import com.ceos20.instagram.domain.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    @Query("SELECT c.id FROM ChatRoom c WHERE c.member1 = :member OR c.member2 = :member")
    List<Long> findByMember(Member member);
}
