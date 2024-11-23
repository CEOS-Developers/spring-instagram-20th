package com.ceos20.instagram.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateMemberInfoRequest {

    private String username;

    private String nickname;

    private String password;

    private String email;

    private String imageUrl;
}
