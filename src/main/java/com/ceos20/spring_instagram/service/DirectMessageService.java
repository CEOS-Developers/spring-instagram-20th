package com.ceos20.spring_instagram.service;

import com.ceos20.spring_instagram.domain.DirectMessage;
import com.ceos20.spring_instagram.domain.Member;
import com.ceos20.spring_instagram.repository.DirectMessageRepository;
import com.ceos20.spring_instagram.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DirectMessageService {

    private final DirectMessageRepository directMessageRepository;
    private final MemberRepository memberRepository;

    @Autowired
    public DirectMessageService(DirectMessageRepository directMessageRepository, MemberRepository memberRepository) {
        this.directMessageRepository = directMessageRepository;
        this.memberRepository = memberRepository;
    }

    // DM 보내기
    public DirectMessage sendDirectMessage(Long senderId, Long receiverId, String text) {
        Optional<Member> sender = memberRepository.findById(senderId);
        Optional<Member> receiver = memberRepository.findById(receiverId);

        if (sender.isEmpty()) {
            throw new IllegalArgumentException("해당 ID의 발신자를 찾을 수 없습니다.");
        }

        if (receiver.isEmpty()) {
            throw new IllegalArgumentException("해당 ID의 수신자를 찾을 수 없습니다.");
        }

        DirectMessage directMessage = DirectMessage.builder()
                .sender(sender.get())
                .receiver(receiver.get())
                .text(text)
                .build();

        return directMessageRepository.save(directMessage); // DM 저장
    }

    // 특정 발신자가 보낸 DM 조회
    public List<DirectMessage> getSentMessages(Long senderId) {
        Optional<Member> sender = memberRepository.findById(senderId);

        if (sender.isEmpty()) {
            throw new IllegalArgumentException("해당 ID의 발신자를 찾을 수 없습니다.");
        }

        return directMessageRepository.findBySender(sender.get());
    }

    // 특정 수신자가 받은 DM 조회
    public List<DirectMessage> getReceivedMessages(Long receiverId) {
        Optional<Member> receiver = memberRepository.findById(receiverId);

        if (receiver.isEmpty()) {
            throw new IllegalArgumentException("해당 ID의 수신자를 찾을 수 없습니다.");
        }

        return directMessageRepository.findByReceiver(receiver.get());
    }

    // 특정 DM 삭제
    public void deleteDirectMessage(Long messageId) {
        Optional<DirectMessage> directMessage = directMessageRepository.findById(messageId);

        if (directMessage.isEmpty()) {
            throw new IllegalArgumentException("해당 ID의 메시지를 찾을 수 없습니다.");
        }

        directMessageRepository.deleteById(messageId);
    }
}
