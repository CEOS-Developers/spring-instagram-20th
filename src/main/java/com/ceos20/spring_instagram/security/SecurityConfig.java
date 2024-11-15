package com.ceos20.spring_instagram.security;

import com.ceos20.spring_instagram.jwt.TokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig {

    private final TokenProvider tokenProvider;
    private final UserDetailsService userDetailsService;

    public SecurityConfig(TokenProvider tokenProvider, UserDetailsService userDetailsService) {
        this.tokenProvider = tokenProvider;
        this.userDetailsService = userDetailsService;
    }

    // PasswordEncoder 빈 생성
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // AuthenticationManager 빈 생성
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);

        // 사용자 서비스와 패스워드 인코더 설정
        authenticationManagerBuilder
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());

        return authenticationManagerBuilder.build();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers(
                                new AntPathRequestMatcher("/api/auth/**"),  // 인증 관련 API
                                new AntPathRequestMatcher("/swagger-ui/**"),  // Swagger UI
                                new AntPathRequestMatcher("/swagger-resources/**"), // Swagger 리소스
                                new AntPathRequestMatcher("/v3/api-docs/**"),  // Swagger JSON API 문서
                                new AntPathRequestMatcher("/api/posts/**")  // 게시글 생성 관련 API
                        )
                )
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers(
                                        new AntPathRequestMatcher("/api/auth/**"),  // 인증 관련 API
                                        new AntPathRequestMatcher("/swagger-ui/**"),  // Swagger UI
                                        new AntPathRequestMatcher("/swagger-resources/**"), // Swagger 리소스
                                        new AntPathRequestMatcher("/v3/api-docs/**") // Swagger JSON API 문서
                                ).permitAll()
                                .anyRequest().authenticated() // 나머지 요청은 인증된 사용자만 접근 가능
                )
                .addFilterBefore(new JwtAuthenticationFilter(tokenProvider, userDetailsService), UsernamePasswordAuthenticationFilter.class); // JWT 인증 필터 추가

        return http.build();  // SecurityFilterChain 객체 반환
    }

}
