package com.ceos20.instagram.message.repository;

import com.ceos20.instagram.chatRoom.domain.ChatRoom;
import com.ceos20.instagram.message.domain.Message;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MessageRepository {

    private final EntityManager em;

    // 메시지 저장
    public void save(Message message) {
        em.persist(message);
    }

    // 특정 채팅방의 메시지 조회
    public List<Message> findByChatRoom(ChatRoom chatRoom) {
        return em.createQuery("select m from Message m where m.chatRoom = :chatRoom", Message.class)
                .setParameter("chatRoom", chatRoom)
                .getResultList();
    }
}
