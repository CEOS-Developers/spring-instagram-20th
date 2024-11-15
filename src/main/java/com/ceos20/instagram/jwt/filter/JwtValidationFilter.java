package com.ceos20.instagram.jwt.filter;

import com.ceos20.instagram.auth.domain.CustomUserDetails;
import com.ceos20.instagram.jwt.JwtUtil;
import com.ceos20.instagram.user.domain.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class JwtValidationFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = jwtUtil.resolveToken(request);

        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            if (token != null && jwtUtil.validateToken(token)) {
                Authentication authentication = jwtUtil.getAuthentication(token);

                String nickname = jwtUtil.getUserNickname(token);

                User user = User.builder()
                        .nickname(nickname)
                        .password("password")
                        .build();

                CustomUserDetails customUserDetails = new CustomUserDetails(user);

                Authentication authToken =
                        new UsernamePasswordAuthenticationToken(nickname, null, List.of());
                SecurityContextHolder.getContext().setAuthentication(authToken);


                filterChain.doFilter(request, response);
                return;
            }
        }



    }
}
