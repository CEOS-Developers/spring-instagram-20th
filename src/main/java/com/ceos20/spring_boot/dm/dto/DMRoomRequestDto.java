package com.ceos20.spring_boot.dm.dto;

import com.ceos20.spring_boot.dm.domain.DMRoom;
import com.ceos20.spring_boot.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DMRoomRequestDto {
    private int notReadDMNum;
    private Long userId1;
    private Long userId2;

    // 정적 팩토리 메서드를 사용해 DMRoom 엔티티로 변환
    public static DMRoom toEntity(DMRoomRequestDto dto, User user1, User user2) {
        return DMRoom.builder()
                .notReadDMNum(dto.getNotReadDMNum())
                .userId_1(user1)
                .userId_2(user2)
                .build();
    }
}
