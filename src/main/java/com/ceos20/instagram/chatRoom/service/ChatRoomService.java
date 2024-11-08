package com.ceos20.instagram.chatRoom.service;

import com.ceos20.instagram.chatRoom.domain.ChatRoom;
import com.ceos20.instagram.chatRoom.dto.GetChatRoomIdResponse;
import com.ceos20.instagram.chatRoom.repository.ChatRoomRepository;
import com.ceos20.instagram.global.exception.ExceptionCode;
import com.ceos20.instagram.global.exception.NotFoundException;
import com.ceos20.instagram.member.domain.Member;
import com.ceos20.instagram.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatRoomService {

    private final MemberRepository memberRepository;
    private final ChatRoomRepository chatRoomRepository;

    @Transactional
    public void createChatRoom(Long memberId, Long friendId) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundException(ExceptionCode.NOT_FOUND_MEMBER));
        Member friend = memberRepository.findById(friendId)
                .orElseThrow(() -> new NotFoundException(ExceptionCode.NOT_FOUND_MEMBER));

        ChatRoom chatRoom = ChatRoom.toEntity(member, friend);
        chatRoomRepository.save(chatRoom);
    }

    public List<GetChatRoomIdResponse> getMyChatRoomIds(Member member) {

        List<Long> chatRoomIds = chatRoomRepository.findByMember(member);
        return chatRoomIds.stream()
                .map(GetChatRoomIdResponse::new)
                .collect(Collectors.toList());
    }
}
