package com.ceos20.spring_instagram.controller;

import com.ceos20.spring_instagram.dto.LoginRequestDTO;
import com.ceos20.spring_instagram.dto.LoginResponseDTO;
import com.ceos20.spring_instagram.dto.RegisterRequestDTO;
import com.ceos20.spring_instagram.dto.RegisterResponseDTO;
import com.ceos20.spring_instagram.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j // Slf4j 어노테이션 사용
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // 회원가입 API
    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDTO> register(@RequestBody RegisterRequestDTO registerRequest) {
        log.info("회원가입 요청: {}", registerRequest.getMembername());  // 요청 받은 회원 정보 로깅
        try {
            RegisterResponseDTO registeredMember = authService.register(registerRequest);
            log.info("회원가입 성공: {}", registerRequest.getMembername());  // 성공적으로 회원가입된 경우
            return ResponseEntity.status(201).body(registeredMember);
        } catch (Exception e) {
            log.error("회원가입 실패: {}. 오류 메시지: {}", registerRequest.getMembername(), e.getMessage());  // 오류 발생 시 로그
            return ResponseEntity.status(500).body(null);
        }
    }

    // 로그인 API
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequest) {
        log.info("로그인 요청: {}", loginRequest.getMembername());  // 요청 받은 로그인 정보 로깅
        try {
            LoginResponseDTO loginResponse = authService.login(loginRequest);
            log.info("로그인 성공: {}", loginRequest.getMembername());  // 로그인 성공 로그
            return ResponseEntity.ok(loginResponse);
        } catch (Exception e) {
            log.error("로그인 실패: {}. 오류 메시지: {}", loginRequest.getMembername(), e.getMessage());  // 오류 발생 시 로그
            return ResponseEntity.status(500).body(null);
        }
    }
}
