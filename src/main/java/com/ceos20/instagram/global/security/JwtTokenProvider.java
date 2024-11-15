package com.ceos20.instagram.global.security;

import com.ceos20.instagram.global.security.dto.JwtToken;
import com.ceos20.instagram.member.domain.Member;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

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

}
