package com.ceos20.spring_instagram.service;

import com.ceos20.spring_instagram.domain.Member;
import com.ceos20.spring_instagram.dto.*;
import com.ceos20.spring_instagram.repository.MemberRepository;
import com.ceos20.spring_instagram.jwt.TokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.Optional;


@Service
public class AuthService {

    private final MemberRepository memberRepository;
    private final TokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthService(MemberRepository memberRepository, TokenProvider tokenProvider, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.tokenProvider = tokenProvider;
        this.passwordEncoder = passwordEncoder;
    }

    public RegisterResponseDTO register(RegisterRequestDTO registerRequestDTO) {
        Member member = Member.builder()
                .membername(registerRequestDTO.getMembername())
                .email(registerRequestDTO.getEmail())
                .password(passwordEncoder.encode(registerRequestDTO.getPassword())) // 비밀번호 해싱
                .profilePicture(registerRequestDTO.getProfilePicture())
                .build();

        Member savedMember = memberRepository.save(member);
        MemberDTO memberDTO = MemberDTO.from(savedMember); // MemberDTO로 변환
        return RegisterResponseDTO.from(memberDTO); // RegisterResponseDTO로 변환하여 반환
    }


    // 로그인 메서드
    public LoginResponseDTO login(LoginRequestDTO loginRequest) {
        Optional<Member> optionalMember = memberRepository.findById(loginRequest.getMemberId()); // memberId로 회원 조회

        // memberId로 사용자를 찾고, 비밀번호를 검증
        if (optionalMember.isPresent()) {
            Member member = optionalMember.get();

            // 비밀번호 검증 (입력된 비밀번호와 저장된 비밀번호가 일치하는지 확인)
            if (passwordEncoder.matches(loginRequest.getPassword(), member.getPassword())) {
                // 비밀번호가 일치하면 Authentication 객체를 생성하여 JWT 토큰을 발급
                Authentication authentication = new UsernamePasswordAuthenticationToken(
                        member, // principal
                        loginRequest.getPassword(), // credentials
                        member.getAuthorities() // 권한
                );

                String token = tokenProvider.createAccessToken(member.getMemberId(), authentication);
                return LoginResponseDTO.builder()
                        .accessToken(token)
                        .member(MemberDTO.from(member)) // 응답에 회원 정보도 포함
                        .build();
            }
        }

        // memberId 또는 비밀번호가 잘못된 경우 예외를 던짐
        throw new RuntimeException("Invalid memberId or password");
    }
}
