package com.ceos20.instagram.user.dto;

import com.ceos20.instagram.user.domain.Follow;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class FollowersResponseDto {
    private String nickname;
    private String imageUrl;

    public static FollowersResponseDto from(final Follow follow) {
        return FollowersResponseDto.builder()
                .nickname(follow.getFollowing().getNickname())
                .imageUrl(follow.getFollowing().getProfileImage())
                .build();
    }
}
