package com.ceos20.spring_instagram.dto;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginResponseDTO {
    private String accessToken; // JWT 토큰
    private MemberDTO member; // 로그인한 사용자 정보
}
