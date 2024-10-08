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
public class SaveUserRequest {

    private String username;

    private String nickname;

    private String password;

    private String email;

    public User toEntity() {
        return User.builder()
                .username(username)
                .nickname(nickname)
                .password(password)
                .email(email)
                .build();
    }
}
