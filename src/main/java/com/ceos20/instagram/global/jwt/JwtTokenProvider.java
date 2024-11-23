package com.ceos20.instagram.global.jwt;

import com.ceos20.instagram.global.exception.ExceptionCode;
import com.ceos20.instagram.global.exception.InvalidTokenException;
import com.ceos20.instagram.global.jwt.dto.JwtToken;
import com.ceos20.instagram.domain.member.domain.Member;
import com.ceos20.instagram.domain.member.repository.MemberRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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

    private final MemberRepository memberRepository;

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
     * @return 토큰이 유효하고 만료되지 않은 경우 true를 반환
     * @throws InvalidTokenException 토큰이 만료되었거나 유효하지 않은 경우
     */
    public boolean validateToken(String token) {
        try {

            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);

            return claims.getBody().getExpiration().after(new Date());
        } catch (io.jsonwebtoken.ExpiredJwtException e) {
            throw new InvalidTokenException(ExceptionCode.EXPIRED_TOKEN);
        } catch (io.jsonwebtoken.SignatureException e) {
            throw new InvalidTokenException(ExceptionCode.INVALID_SIGNATURE);
        } catch (io.jsonwebtoken.MalformedJwtException e) {
            throw new InvalidTokenException(ExceptionCode.MALFORMED_TOKEN);
        } catch (IllegalArgumentException e) {
            throw new InvalidTokenException(ExceptionCode.ILLEGAL_ARGUMENT);
        }
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

    public void reissue(HttpServletRequest request, HttpServletResponse response) {

        String refreshToken = extractRefreshTokenFromCookie(request);
        Authentication authentication = getAuthentication(refreshToken);
        Member member = getMemberFromAuthentication(authentication);
        String newAccessToken = createAccessToken(member);

        if (!validateToken(refreshToken)) {
            refreshToken = createRefreshToken(member);
            setRefreshTokenToCookie(refreshToken, response);
        }
        response.setHeader("Authorization", "Bearer " + newAccessToken);
    }

    public Member getMemberFromAuthentication(Authentication authentication) {

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String nickname = userDetails.getUsername();

        return memberRepository.findByNickname(nickname).orElse(null);
    }

    public String extractRefreshTokenFromCookie(HttpServletRequest request) {

        Cookie[] cookies = request.getCookies();
        for(Cookie cookie : cookies) {
            if (cookie.getName().equals("refreshToken")) {
                return cookie.getValue();
            }
        }
        throw new InvalidTokenException(ExceptionCode.NOT_FOUND_REFRESH_TOKEN);
    }

    public void setRefreshTokenToCookie(String refreshToken, HttpServletResponse response) {

        Cookie refreshCookie = new Cookie("refreshToken", refreshToken);
        refreshCookie.setHttpOnly(true);
        refreshCookie.setSecure(true);
        refreshCookie.setPath("/");
        refreshCookie.setMaxAge(7 * 24 * 60 * 60 * 1000);
        response.addCookie(refreshCookie);
    }
}
