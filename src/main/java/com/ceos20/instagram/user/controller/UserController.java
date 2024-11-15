package com.ceos20.instagram.user.controller;

import com.ceos20.instagram.jwt.JwtUtil;
import com.ceos20.instagram.user.domain.User;
import com.ceos20.instagram.auth.dto.JoinRequestDto;
import com.ceos20.instagram.user.dto.UserPageResponseDto;
import com.ceos20.instagram.user.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/accounts")
public class UserController {
    private final UserService userService;

    @GetMapping("/{nickname}")
    public ResponseEntity getUserPage(@PathVariable String nickname) {
        final UserPageResponseDto response = userService.getUserPage(nickname);

        return ResponseEntity.ok().body(response);
    }



}
