package com.ceos20.instagram.postLike.dto;

import com.ceos20.instagram.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetUserLikeResponse {

    private String nickname;

    public static GetUserLikeResponse fromEntity(User user) {
        return GetUserLikeResponse.builder()
                .nickname(user.getNickname())
                .build();
    }
}
