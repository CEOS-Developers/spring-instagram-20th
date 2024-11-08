package com.ceos20.instagram.user.dto;

import com.ceos20.instagram.user.domain.Follow;
import com.ceos20.instagram.user.domain.User;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class FollowCreateDto {
    private User accessUser;
    private User followingUser;

    public Follow toEntity(User accessUser, User followingUser) {
        return Follow.builder()
                .following(followingUser)
                .follower(accessUser)
                .build();
    }

}
