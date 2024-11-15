package com.ceos20.instagram.jwt.filter;

import com.ceos20.instagram.jwt.JwtUtil;
import com.ceos20.instagram.auth.service.RedisService;
import com.ceos20.instagram.auth.dto.LoginResponseDto;
import com.ceos20.instagram.user.domain.Role;
import com.ceos20.instagram.user.domain.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.time.Duration;
import java.util.Collection;

@Slf4j
@Component
@RequiredArgsConstructor
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    private final JwtUtil jwtUtil;
    private final RedisService redisService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String nickname = userDetails.getUsername();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String role = authorities.stream().findFirst().get().getAuthority();


        String accessToken = jwtUtil.generateAccessToken(nickname, role);
        String refreshToken = jwtUtil.generateRefreshToken(nickname, role);

        redisService.save(nickname, refreshToken, Duration.ofMillis(jwtUtil.getExpiration(refreshToken)));

//        Cookie accessTokenCookie = createCookie(accessToken, "accessToken");
        Cookie refreshTokenCookie = createCookie(refreshToken, "refreshToken");

        response.addHeader("Authorization", "Bearer " + accessToken);

        String jsonResponse = new ObjectMapper().writeValueAsString(new LoginResponseDto(HttpServletResponse.SC_OK, "로그인 성공"));
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write(jsonResponse);

//        response.addCookie(accessTokenCookie);
        response.addCookie(refreshTokenCookie);
    }

    private Cookie createCookie(String accessToken, String cookieName) {
        Cookie accessTokenCookie = new Cookie(accessToken, cookieName);
        long expiration = jwtUtil.v(accessToken);
        int maxAge = (int)((expiration - new Date(System.currentTimeMillis()).getTime()) / 1000);
        accessTokenCookie.setMaxAge(maxAge);
        accessTokenCookie.setPath("/");
        accessTokenCookie.setHttpOnly(true);
        accessTokenCookie.setSecure(false);

        return accessTokenCookie;
    }
}
