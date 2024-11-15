package com.ceos20.instagram.auth.dto;

import com.ceos20.instagram.member.domain.Member;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequest {

    @Size(min = 3, max = 25, message = "사용자 이름은 3자 이상, 25자 이하로 입력해주세요.")
    @NotEmpty(message = "사용자 이름은 필수 입력값입니다.")
    private String username;

    @Size(min = 3, max = 25, message = "사용자 id는 3자 이상, 25자 이하로 입력해주세요.")
    @NotEmpty(message = "사용자 id는 필수 입력값입니다.")
    private String nickname;

    @NotEmpty(message = "비밀번호는 필수 입력값입니다.")
    private String password;

    @NotEmpty(message = "비밀번호 확인은 필수 입력값입니다.")
    private String passwordCheck;

    @Email
    @NotEmpty(message = "사용자 이메일은 필수 입력값입니다.")
    private String email;


    public Member toEntity(PasswordEncoder passwordEncoder) {

        return Member.builder()
                .username(username)
                .nickname(nickname)
                .password(passwordEncoder.encode(password))
                .email(email)
                .build();
    }
}
