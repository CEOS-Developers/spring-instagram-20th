package com.ceos20.instagram.auth.service;

import com.ceos20.instagram.auth.dto.SignInRequest;
import com.ceos20.instagram.auth.dto.SignUpRequest;
import com.ceos20.instagram.global.exception.ConflictException;
import com.ceos20.instagram.global.exception.ExceptionCode;
import com.ceos20.instagram.global.jwt.JwtTokenProvider;
import com.ceos20.instagram.global.jwt.dto.JwtToken;
import com.ceos20.instagram.member.domain.Member;
import com.ceos20.instagram.member.service.MemberService;
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
    public JwtToken signIn(SignInRequest signInRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        signInRequest.getNickname(),
                        signInRequest.getPassword()));

        Member member = memberService.getMemberByNickname(signInRequest.getNickname());
        return jwtTokenProvider.getToken(member);
    }
}
