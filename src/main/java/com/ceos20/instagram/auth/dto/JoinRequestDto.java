package com.ceos20.instagram.auth.dto;

import com.ceos20.instagram.user.domain.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import com.ceos20.instagram.user.domain.User;

@Builder
@Getter
@ToString
public class JoinRequestDto {
    private String nickname;
    private String password;

    public User toEntity(JoinRequestDto joinRequestDto, String encPassword) {
        return User.builder()
                .nickname(joinRequestDto.getNickname())
                .password(encPassword)
                .role(Role.ROLE_USER)
                .build();
    }
}
