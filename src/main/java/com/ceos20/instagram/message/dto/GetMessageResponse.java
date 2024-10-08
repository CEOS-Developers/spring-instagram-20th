package com.ceos20.instagram.message.dto;

import com.ceos20.instagram.message.domain.Message;
import com.ceos20.instagram.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetMessageResponse {

    private Long senderId;

    private String senderNickname;

    private String senderImageUrl;

    private String content;

    private LocalDateTime sendTime;

    public static GetMessageResponse fromEntity(Message message) {
        return GetMessageResponse.builder()
                .senderId(message.getSender().getId())
                .senderNickname(message.getSender().getNickname())
                .senderImageUrl(message.getSender().getImageUrl())
                .content(message.getContent())
                .sendTime(message.getCreatedAt())
                .build();
    }
}
