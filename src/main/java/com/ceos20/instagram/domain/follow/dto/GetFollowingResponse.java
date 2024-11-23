package com.ceos20.instagram.domain.follow.dto;

import com.ceos20.instagram.domain.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetFollowingResponse {

    private Long userId;

    private String nickname;

    public static GetFollowingResponse fromEntity(Member following) {
        return GetFollowingResponse.builder()
                .userId(following.getId())
                .nickname(following.getNickname())
                .build();
    }
}
