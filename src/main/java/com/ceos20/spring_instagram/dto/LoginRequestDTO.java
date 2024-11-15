package com.ceos20.spring_instagram.dto;
import lombok.Getter;

@Getter

public class LoginRequestDTO {
    private Long memberId;
    private String membername;
    private String password;
}
