package com.ceos20.instagram.follow.dto;

import com.ceos20.instagram.user.domain.User;
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

    public static GetFollowingResponse fromEntity(User following) {
        return GetFollowingResponse.builder()
                .userId(following.getId())
                .nickname(following.getNickname())
                .build();
    }
}
