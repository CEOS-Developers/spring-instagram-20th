package com.ceos20.instagram.user.dto;

import com.ceos20.instagram.user.domain.User;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class UserCreateDto {
    private String nickname;
    private String password;

    public User toEntity() {
        return User.builder()
                .nickname(nickname)
                .password(password)
                .build();
    }

}
