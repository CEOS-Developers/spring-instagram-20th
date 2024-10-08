package com.ceos20.instagram.message.service;

import com.ceos20.instagram.chatRoom.domain.ChatRoom;
import com.ceos20.instagram.chatRoom.repository.ChatRoomRepository;
import com.ceos20.instagram.message.domain.Message;
import com.ceos20.instagram.message.dto.CreateMessageRequest;
import com.ceos20.instagram.message.dto.GetMessageResponse;
import com.ceos20.instagram.message.repository.MessageRepository;
import com.ceos20.instagram.user.domain.User;
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

    // message 생성
    @Transactional
    public void createMessage(CreateMessageRequest createMessageRequest, User user) {

        ChatRoom chatRoom = chatRoomRepository.findById(createMessageRequest.getRoomId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 chatRoom 입니다."));

        Message message = createMessageRequest.toEntity(user, chatRoom);
        messageRepository.save(message);
    }

    // 특정 채팅방의 모든 message 리스트 반환
    public List<GetMessageResponse> getMessageInChatRoom(Long chatRoomId) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 chatRoom 입니다."));

        List<Message> messages = messageRepository.findByChatRoom(chatRoom);
        return messages.stream()
                .map(message -> GetMessageResponse.fromEntity(message))
                .collect(Collectors.toList());
    }
}
