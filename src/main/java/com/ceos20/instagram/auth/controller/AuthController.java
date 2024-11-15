package com.ceos20.instagram.auth.controller;

import com.ceos20.instagram.auth.dto.JoinRequestDto;
import com.ceos20.instagram.auth.dto.LoginRequestDto;
import com.ceos20.instagram.auth.service.AuthService;
import com.ceos20.instagram.global.exception.ExceptionCode;
import com.ceos20.instagram.global.exception.NotFoundException;
import com.ceos20.instagram.jwt.JwtTokenProvider;
import com.ceos20.instagram.jwt.JwtUtil;
import com.ceos20.instagram.user.domain.User;
import com.ceos20.instagram.user.repository.UserRepository;
import com.ceos20.instagram.user.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/accounts")
public class AuthController {
    private final AuthService authService;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/user/signup")
    public ResponseEntity<User> createUser(@RequestBody JoinRequestDto joinRequestDto) {
        User newUser = authService.create(joinRequestDto);
        URI uri = URI.create(ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/accounts/user/"+newUser.getId()).toUriString());

        return ResponseEntity.created(uri).body(newUser);
    }


    @PostMapping("/token/refresh")
    public ResponseEntity<String> reissue(HttpServletRequest request, HttpServletResponse respone, @CookieValue(name="refreshToken", required = false) String refreshToken) {
        if (refreshToken == null) {
            return ResponseEntity.badRequest().body("Refresh token is required");
        }

        if (!jwtUtil.validateToken(refreshToken)) {
            return ResponseEntity.badRequest().body("refreshToken expired");
        }

        try {
            String accessToken = authService.reissue(refreshToken);
            respone.setHeader("Authorization", "Bearer " + accessToken);

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("/user/logout")
    public ResponseEntity<String> logout(HttpServletResponse response) {
        SecurityContextHolder.clearContext();

        Cookie refreshToken = deleteCookie("refreshToken");

        return ResponseEntity.ok().body("Logout successs");
    }

    private Cookie deleteCookie(String CookieName) {
        Cookie cookie = new Cookie(CookieName, null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        return cookie;
    }
}
