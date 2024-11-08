package com.ceos20.instagram.message.repository;


import com.ceos20.instagram.chatRoom.domain.ChatRoom;
import com.ceos20.instagram.message.domain.Message;
import com.ceos20.instagram.member.domain.Member;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
public class MessageRepositoryTest {

    private final MessageRepository messageRepository;
    private final EntityManager em;

    @Autowired
    public MessageRepositoryTest(MessageRepository messageRepository, EntityManager em) {
        this.messageRepository = messageRepository;
        this.em = em;
    }

    @Test
    @DisplayName("특정 채팅방의 메시지 조회 테스트")
    public void findByChatRoomTest() {
        // given
        Member member1 = Member.builder()
                .username("user1")
                .nickname("user1")
                .password("password")
                .build();
        em.persist(member1);

        Member member2 = Member.builder()
                .username("user2")
                .nickname("user2")
                .password("password")
                .build();
        em.persist(member2);

        ChatRoom chatRoom = ChatRoom.builder()
                .member1(member1)
                .member2(member2)
                .build();
        em.persist(chatRoom);

        Message message1 = Message.builder()
                .chatRoom(chatRoom)
                .sender(member1)
                .content("user1이 보낸 메시지")
                .build();
        em.persist(message1);

        Message message2 = Message.builder()
                .chatRoom(chatRoom)
                .sender(member2)
                .content("user2가 보낸 메시지")
                .build();
        em.persist(message2);

        // when
        List<Message> findMessages = messageRepository.findByChatRoom(chatRoom);

        // then
        assertEquals(2, findMessages.size());

        for(Message findMessage : findMessages) {
            System.out.println("content: "+ findMessage.getContent());
        }
    }
}
