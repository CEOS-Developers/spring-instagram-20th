package com.ceos20.instagram.domain.message.dto;

import com.ceos20.instagram.domain.chatRoom.domain.ChatRoom;
import com.ceos20.instagram.domain.message.domain.Message;
import com.ceos20.instagram.domain.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateMessageRequest {

    private String content;

    private Long roomId;

    public Message toEntity(Member sender, ChatRoom chatRoom) {
        return Message.builder()
                .content(content)
                .sender(sender)
                .chatRoom(chatRoom)
                .build();
    }
}
