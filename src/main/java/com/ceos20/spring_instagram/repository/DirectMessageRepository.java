package com.ceos20.spring_instagram.repository;

import com.ceos20.spring_instagram.domain.DirectMessage;
import com.ceos20.spring_instagram.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DirectMessageRepository extends JpaRepository<DirectMessage, Long> {
    // 특정 발신자가 보낸 메시지 조회
    List<DirectMessage> findBySender(Member sender);

    // 특정 수신자가 받은 메시지 조회
    List<DirectMessage> findByReceiver(Member receiver);
}
