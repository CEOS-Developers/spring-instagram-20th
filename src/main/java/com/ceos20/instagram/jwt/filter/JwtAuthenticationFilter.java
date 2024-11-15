package com.ceos20.instagram.jwt.filter;

import com.ceos20.instagram.auth.dto.LoginRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String contentType = request.getContentType();
        String nickname = "";
        String password = "";

        if(contentType.equals(MediaType.APPLICATION_JSON_VALUE)){
            try {
                LoginRequestDto loginRequest = new ObjectMapper().readValue(request.getReader(), LoginRequestDto.class);
                nickname = loginRequest.getNickname();
                password = loginRequest.getPassword();
            } catch (IOException e) {
                throw new AuthenticationServiceException("잘못된 key, name으로 요청했습니다.", e);
            }
        }
        else if(contentType.equals(MediaType.APPLICATION_FORM_URLENCODED_VALUE)) {
            nickname = this.obtainPassword(request);
        }

        UsernamePasswordAuthenticationToken unauthenticated = new UsernamePasswordAuthenticationToken(nickname, password);

        return super.getAuthenticationManager().authenticate(unauthenticated);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,Authentication authResult) throws IOException, ServletException {
        log.info("Security Login >> 인증 성공");
        final String nickname = authResult.getName();

        AuthenticationSuccessHandler handler = this.getSuccessHandler();
        handler.onAuthenticationSuccess(request, response, authResult);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        log.info("Security Login >> 인증 실패");

        AuthenticationFailureHandler handler = this.getFailureHandler();
        handler.onAuthenticationFailure(request, response, failed);
    }
}
