package com.ceos20.spring_boot.dm.dto;

import com.ceos20.spring_boot.dm.domain.DM;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
public class DMResponseDto {
    private Long dmId;
    private String dmContent;
    private Timestamp sentAt;
    private Boolean isRead;
    private String emotion;
    private Long refDMId;
    private Long senderId;
    private Long receiverId;
    private Long dmRoomId;

    private DMResponseDto(DM dm) {
        this.dmId = dm.getDmId();
        this.dmContent = dm.getDmContent();
        this.sentAt = dm.getSentAt();
        this.isRead = dm.getIsRead();
        this.emotion = dm.getEmotion();
        this.refDMId = dm.getRefDMId() != null ? dm.getRefDMId().getDmId() : null;
        this.senderId = dm.getSenderId().getUserId();
        this.receiverId = dm.getReceiverId().getUserId();
        this.dmRoomId = dm.getDmRoomId().getDmRoomId();
    }

    // 정적 팩토리 메서드 - DM 엔티티를 DMResponseDto로 변환
    public static DMResponseDto fromEntity(DM dm) {
        return new DMResponseDto(dm);
    }
}
