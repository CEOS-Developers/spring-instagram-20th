package com.ceos20.instagram.domain.message.service;

import com.ceos20.instagram.domain.chatRoom.domain.ChatRoom;
import com.ceos20.instagram.domain.chatRoom.repository.ChatRoomRepository;
import com.ceos20.instagram.domain.message.domain.Message;
import com.ceos20.instagram.domain.message.dto.CreateMessageRequest;
import com.ceos20.instagram.domain.message.dto.GetMessageResponse;
import com.ceos20.instagram.domain.message.repository.MessageRepository;
import com.ceos20.instagram.global.exception.ExceptionCode;
import com.ceos20.instagram.global.exception.NotFoundException;
import com.ceos20.instagram.domain.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MessageService {

    private final ChatRoomRepository chatRoomRepository;
    private final MessageRepository messageRepository;

    @Transactional
    public void createMessage(CreateMessageRequest createMessageRequest, Member member) {

        ChatRoom chatRoom = chatRoomRepository.findById(createMessageRequest.getRoomId())
                .orElseThrow(() -> new NotFoundException(ExceptionCode.NOT_FOUND_CHATROOM));

        Message message = createMessageRequest.toEntity(member, chatRoom);
        messageRepository.save(message);
    }

    public List<GetMessageResponse> getMessageInChatRoom(Long chatRoomId) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new NotFoundException(ExceptionCode.NOT_FOUND_CHATROOM));

        List<Message> messages = messageRepository.findByChatRoom(chatRoom);
        return messages.stream()
                .map(message -> GetMessageResponse.fromEntity(message))
                .collect(Collectors.toList());
    }
}
