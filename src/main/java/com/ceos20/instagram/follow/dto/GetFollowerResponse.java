package com.ceos20.instagram.follow.dto;

import com.ceos20.instagram.follow.domain.Follow;
import com.ceos20.instagram.user.domain.User;
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

    public static GetFollowerResponse fromEntity(User follower) {
        return GetFollowerResponse.builder()
                .userId(follower.getId())
                .nickname(follower.getNickname())
                .build();
    }
}
