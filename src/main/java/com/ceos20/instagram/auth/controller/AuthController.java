package com.ceos20.instagram.auth.controller;

import com.ceos20.instagram.auth.dto.SignInRequest;
import com.ceos20.instagram.auth.dto.SignUpRequest;
import com.ceos20.instagram.auth.service.AuthService;
import com.ceos20.instagram.common.response.ApiResponse;
import com.ceos20.instagram.common.response.ResponseBuilder;
import com.ceos20.instagram.global.jwt.dto.JwtToken;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signUp")
    private ResponseEntity<ApiResponse<Void>> signUp(@RequestBody SignUpRequest signUpRequest) {

        authService.signUp(signUpRequest);
        return ResponseBuilder.createApiResponse("회원가입 성공", null);
    }

    @PostMapping("/signIn")
    private ResponseEntity<ApiResponse<JwtToken>> signIn(@RequestBody SignInRequest signInRequest) {

        JwtToken token = authService.signIn(signInRequest);
        return ResponseBuilder.createApiResponse("로그인 성공", token);
    }
}
