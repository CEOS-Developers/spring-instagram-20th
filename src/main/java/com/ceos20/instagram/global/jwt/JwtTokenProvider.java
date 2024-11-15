package com.ceos20.instagram.global.jwt;

import com.ceos20.instagram.global.jwt.dto.JwtToken;
import com.ceos20.instagram.member.domain.Member;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final UserDetailsService userDetailsService;
    private final String secretKey = generateSecretKey();
    private static final long accessTokenExpirationTime = 30 * 60 * 1000L;
    private static final long refreshTokenExpirationTime = 7 * 24 * 60 * 60 * 1000L;

    /**
     * jwt 서명 및 인코딩을 위한 랜덤 secretKey 생성
     * @return Base64로 인코딩된 256비트 Secret Key 문자열
     */
    private String generateSecretKey() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] keyBytes = new byte[32];
        secureRandom.nextBytes(keyBytes);
        return Base64.getEncoder().encodeToString(keyBytes);
    }

    /**
     * 주어진 회원 정보를 바탕으로 jwt accessToken 생성
     * @param member 토큰 생성에 필요한 회원 정보
     * @return 생성된 accessToken 문자열 반환
     */
    private String createAccessToken(Member member) {

        Date now = new Date();

        return Jwts.builder()
                .setHeaderParam("type", "jwt")
                .claim("sub", member.getId())
                .claim("nickname", member.getNickname())
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + accessTokenExpirationTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }


    /**
     * 주어진 회원 정보를 바탕으로 jwt refreshToken 생성
     * @param member 토큰 생성에 필요한 회원 정보
     * @return 생성된 refreshToken 문자열 반환
     */
    private String createRefreshToken(Member member) {

        Date now = new Date();

        return Jwts.builder()
                .setHeaderParam("type", "jwt")
                .claim("sub", member.getId())
                .claim("nickname", member.getNickname())
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + refreshTokenExpirationTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    /**
     * accessToken, refreshToken 발급
     * @param member 토큰 생성에 필요한 회원 정보
     * @return accessToken과 refreshToken이 담긴 JwtToken 반환
     */
    public JwtToken getToken(Member member) {

        return JwtToken.builder()
                .accessToken(createAccessToken(member))
                .refreshToken(createRefreshToken(member))
                .build();
    }

    /**
     * 요청 헤더에서 jwt 토큰 추출
     * @param request 클라이언트의 HTTP 요청 객체
     * @return Authorization 헤더에 포함된 JWT 토큰 문자열, 없다면 null 반환
     */
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    /**
     * 토큰 유효성 검사
     * @param token accessToken
     * @return 유효하면 true, 유효하지 않으면 false 반환
     */
    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return claims.getBody().getExpiration().after(new Date());
        } catch (io.jsonwebtoken.ExpiredJwtException e) {
            System.out.println("토큰이 만료되었습니다: " + e.getMessage());
        } catch (io.jsonwebtoken.SignatureException e) {
            System.out.println("서명이 유효하지 않습니다: " + e.getMessage());
        } catch (io.jsonwebtoken.MalformedJwtException e) {
            System.out.println("잘못된 JWT 형식입니다: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("토큰이 비어있거나 잘못된 형식입니다: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("알 수 없는 오류가 발생했습니다: " + e.getMessage());
        }
        return false;
    }

    /**
     * 주어진 JWT 토큰에서 사용자 정보를 추출
     * @param token JWT 토큰
     * @return 추출된 사용자 정보를 포함한 Authentication 객체를 생성해 반환
     */
    public Authentication getAuthentication(String token) {

        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();

        String nickname = claims.get("nickname", String.class);

        UserDetails userDetails = userDetailsService.loadUserByUsername(nickname);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

}
