package com.ceos20.instagram.user.dto;

import com.ceos20.instagram.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetUserInfoResponse {

    private String username;

    private String nickname;

    private String email;

    private String imageUrl;

    public static GetUserInfoResponse fromEntity(User user) {
        return GetUserInfoResponse.builder()
                .username(user.getUsername())
                .nickname(user.getNickname())
                .email(user.getEmail())
                .imageUrl(user.getImageUrl())
                .build();
    }
}
