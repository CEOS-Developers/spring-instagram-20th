package com.ceos20.spring_boot.dm.dto;

import com.ceos20.spring_boot.dm.domain.DMRoom;
import lombok.Getter;

@Getter
public class DMRoomResponseDto {
    private Long dmRoomId;
    private int notReadDMNum;
    private Long userId1;
    private Long userId2;

    private DMRoomResponseDto(DMRoom dmRoom) {
        this.dmRoomId = dmRoom.getDmRoomId();
        this.notReadDMNum = dmRoom.getNotReadDMNum();
        this.userId1 = dmRoom.getUserId_1().getUserId();
        this.userId2 = dmRoom.getUserId_2().getUserId();
    }

    // 정적 팩토리 메서드를 사용해 DMRoomResponseDto 생성
    public static DMRoomResponseDto fromEntity(DMRoom dmRoom) {
        return new DMRoomResponseDto(dmRoom);
    }
}
