package com.ceos20.instagram.config;

import com.ceos20.instagram.jwt.JwtTokenProvider;
import com.ceos20.instagram.jwt.JwtUtil;
import com.ceos20.instagram.jwt.filter.JwtValidationFilter;
import com.ceos20.instagram.jwt.filter.LoginFailHandler;
import com.ceos20.instagram.jwt.filter.LoginSuccessHandler;
import io.jsonwebtoken.impl.JwtTokenizer;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import com.ceos20.instagram.jwt.filter.JwtAuthenticationFilter;
import java.util.Collections;
import java.util.List;

@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Value("${spring.security.cors.allowed-methods}")
    private String[] ALLOW_METHODS;
    @Value("${spring.security.cors.allow-origins}")
    private String ALLOW_CROSS_ORIGIN_DOMAIN;

    private final JwtUtil jwtUtil;
    private final AuthenticationConfiguration authenticationConfiguration;
    private final LoginSuccessHandler loginSuccessHandler;
    private final LoginFailHandler loginFailHandler;
    private final JwtTokenProvider jwtTokenProvider;

    // 회원의 패스워드 암호화
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){

        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtAuthenticationFilter loginAuthenticationFilter() throws Exception {
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter();
        jwtAuthenticationFilter.setAuthenticationManager(authenticationManager(authenticationConfiguration));
        jwtAuthenticationFilter.setFilterProcessesUrl("/accounts/login");
        jwtAuthenticationFilter.setAuthenticationSuccessHandler(loginSuccessHandler);
        jwtAuthenticationFilter.setAuthenticationFailureHandler(loginFailHandler);
        return jwtAuthenticationFilter;
    }

    // 시큐리티 필터 설정
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, JwtUtil jwtUtil) throws Exception {
        final String[] ALL_URL = new String[] {"/accounts/login", "/accounts/user/signup", "/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**"};

        http
                .cors((cors -> cors.configurationSource(new CorsConfigurationSource() {
                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                        CorsConfiguration configuration = new CorsConfiguration();

                        configuration.setAllowedOrigins(List.of(ALLOW_CROSS_ORIGIN_DOMAIN));
                        configuration.setAllowedMethods(List.of(ALLOW_METHODS));
                        configuration.setAllowedHeaders(Collections.singletonList("*"));
                        configuration.setAllowCredentials(true);
                        configuration.setMaxAge(3600L);

                        return configuration;
                    }
                })));
        http
                .csrf((auth) -> auth.disable());
        http
                .formLogin((auth) -> auth.disable());
        http
                .authorizeHttpRequests((auth) -> auth
                .requestMatchers(ALL_URL).permitAll()
                .anyRequest().authenticated());
        http
                .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http
                .addFilterBefore(new JwtValidationFilter(jwtUtil), JwtAuthenticationFilter.class)
                .addFilterAt(loginAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();


    }

    // authenticationManger를 Bean 등록
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}