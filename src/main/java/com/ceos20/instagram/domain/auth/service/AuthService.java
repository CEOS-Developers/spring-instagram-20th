package com.ceos20.instagram.domain.auth.service;

import com.ceos20.instagram.domain.auth.dto.SignInRequest;
import com.ceos20.instagram.domain.auth.dto.SignUpRequest;
import com.ceos20.instagram.global.exception.ConflictException;
import com.ceos20.instagram.global.exception.ExceptionCode;
import com.ceos20.instagram.global.jwt.JwtTokenProvider;
import com.ceos20.instagram.global.jwt.dto.JwtToken;
import com.ceos20.instagram.domain.member.domain.Member;
import com.ceos20.instagram.domain.member.service.MemberService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final MemberService memberService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public void signUp(SignUpRequest signUpRequest) {

        if (memberService.nicknameAlreadyExists(signUpRequest.getNickname()))
            throw new ConflictException(ExceptionCode.ALREADY_EXISTS_NICKNAME);

        if (memberService.emailAlreadyExists(signUpRequest.getEmail()))
            throw new ConflictException(ExceptionCode.ALREADY_EXISTS_MEMBER);

        memberService.saveMember(signUpRequest);
    }

    @Transactional
    public void adminSignUp(SignUpRequest signUpRequest) {

        if (memberService.nicknameAlreadyExists(signUpRequest.getNickname()))
            throw new ConflictException(ExceptionCode.ALREADY_EXISTS_NICKNAME);

        if (memberService.emailAlreadyExists(signUpRequest.getEmail()))
            throw new ConflictException(ExceptionCode.ALREADY_EXISTS_MEMBER);

        memberService.saveAdmin(signUpRequest);
    }

    @Transactional
    public void signIn(SignInRequest signInRequest, HttpServletResponse response) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        signInRequest.getNickname(),
                        signInRequest.getPassword()));

        Member member = memberService.getMemberByNickname(signInRequest.getNickname());
        JwtToken token = jwtTokenProvider.getToken(member);

        // 헤더 : accessToken
        response.setHeader("Authorization", "Bearer " + token.getAccessToken());

        // 쿠키 : refreshToken
        jwtTokenProvider.setRefreshTokenToCookie(token.getRefreshToken(), response);
    }
}
