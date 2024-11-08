package com.ceos20.instagram.follow.dto;

import com.ceos20.instagram.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetFollowerResponse {

    private Long userId;

    private String nickname;

    public static GetFollowerResponse fromEntity(Member follower) {
        return GetFollowerResponse.builder()
                .userId(follower.getId())
                .nickname(follower.getNickname())
                .build();
    }
}
