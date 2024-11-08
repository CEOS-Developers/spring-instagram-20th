package com.ceos20.instagram.user.dto;

import com.ceos20.instagram.user.domain.Follow;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class FollowingsResponseDto {
    private String nickname;
    private String imageUrl;

    public static FollowingsResponseDto from(final Follow follow) {
        return FollowingsResponseDto.builder()
                .nickname(follow.getFollower().getNickname())
                .imageUrl(follow.getFollower().getProfileImage())
                .build();
    }
}
