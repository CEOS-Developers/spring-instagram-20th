package com.ceos20.instagram.global.jwt.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class JwtToken {

    private String accessToken;
    private String refreshToken;
}
