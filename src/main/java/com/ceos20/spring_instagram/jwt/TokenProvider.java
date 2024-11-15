package com.ceos20.spring_instagram.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class TokenProvider implements InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(TokenProvider.class);

    private Key key; // JWT 서명에 사용될 키
    private final UserDetailsService userDetailsService; // 사용자 정보를 가져오기 위한 서비스

    @Value("${jwt.secret}")
    private String secret; // 비밀 키를 외부에서 주입받음

    @Value("${jwt.expiration_time}")
    private long tokenValidTime; // 토큰의 유효 시간

    // 생성자 주입을 통해 UserDetailsService를 주입받음
    public TokenProvider(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    // Bean 초기화 후 호출되어 비밀 키를 기반으로 HMAC SHA 키를 생성
    @Override
    public void afterPropertiesSet() {
        // 비밀 키가 256비트 이상이어야 하므로, 이를 보장하기 위해 256비트 이상인 키를 사용하는 방법
        if (secret.length() < 32) {
            throw new IllegalArgumentException("비밀 키의 길이가 256비트 이상이어야 합니다.");
        }

        byte[] keyBytes = secret.getBytes(); // 비밀 키를 바이트 배열로 변환
        this.key = Keys.hmacShaKeyFor(keyBytes); // HMAC SHA 키 생성
    }

    // 액세스 토큰을 생성하는 메서드
    public String createAccessToken(Long id, Authentication authentication) {
        String authorities =
                authentication.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.joining(","));

        return Jwts.builder()
                .setSubject(String.valueOf(id)) // 토큰의 주체에 사용자 ID 설정
                .claim("auth", authorities) // 사용자 권한을 claim에 추가
                .setIssuedAt(new Date()) // 토큰 발행 시간 설정
                .setExpiration(new Date(System.currentTimeMillis() + tokenValidTime)) // 토큰 만료 시간 설정
                .signWith(key, SignatureAlgorithm.HS256) // 키와 알고리즘으로 서명
                .compact(); // 토큰 문자열로 반환
    }

    // 토큰에서 사용자 ID를 추출하는 메서드
    public String getTokenUserId(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    // 인증 정보를 반환하는 메서드
    public Authentication getAuthentication(String token) {
        UserDetails userDetails =
                (UserDetails) userDetailsService.loadUserByUsername(getTokenUserId(token));
        return new UsernamePasswordAuthenticationToken(
                userDetails, token, userDetails.getAuthorities());
    }

    // 토큰을 요청에서 추출하는 메서드
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); // "Bearer "를 제외한 토큰만 반환
        }
        return null;
    }

    // 토큰 유효성 검증 메서드
    public boolean validateAccessToken(String token) {
        try {
            // 토큰을 파싱하여 유효성을 검증
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true; // 유효한 경우 true 반환
        } catch (ExpiredJwtException e) {
            logger.info("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            logger.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            logger.info("JWT 토큰이 잘못되었습니다.");
        } catch (Exception e) {
            logger.error("JWT 토큰 검증 중 오류 발생: {}", e.getMessage());
        }
        return false; // 유효하지 않은 경우 false 반환
    }
}
