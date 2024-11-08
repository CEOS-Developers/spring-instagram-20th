package com.ceos20.spring_boot.dm.dto;

import com.ceos20.spring_boot.dm.domain.DM;
import com.ceos20.spring_boot.dm.domain.DMRoom;
import com.ceos20.spring_boot.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DMRequestDto {
    private String dmContent;
    private Timestamp sentAt;
    private Boolean isRead;
    private String emotion;
    private Long refDMId;
    private Long senderId;
    private Long receiverId;
    private Long dmRoomId;

    // 정적 팩토리 메서드 - DMRequestDto를 DM 엔티티로 변환
    public static DM toEntity(DMRequestDto dto, User sender, User receiver, DMRoom dmRoom, DM refDM) {
        return DM.builder()
                .dmContent(dto.getDmContent())
                .sentAt(dto.getSentAt())
                .isRead(dto.getIsRead())
                .emotion(dto.getEmotion())
                .refDMId(refDM)
                .senderId(sender)
                .receiverId(receiver)
                .dmRoomId(dmRoom)
                .build();
    }
}
