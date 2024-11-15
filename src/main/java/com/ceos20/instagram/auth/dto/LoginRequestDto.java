package com.ceos20.instagram.auth.dto;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class LoginRequestDto {
    String nickname;
    String password;

}
