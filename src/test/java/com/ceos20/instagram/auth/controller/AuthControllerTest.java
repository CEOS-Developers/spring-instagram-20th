package com.ceos20.instagram.auth.controller;

import com.ceos20.instagram.domain.auth.dto.SignInRequest;
import com.ceos20.instagram.domain.auth.dto.SignUpRequest;
import com.ceos20.instagram.domain.auth.service.AuthService;
import com.ceos20.instagram.global.jwt.dto.JwtToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuthService authService;

    private SignUpRequest signUpRequest;
    private SignInRequest signInRequest;
    private JwtToken jwtToken;

    @BeforeEach
    public void setup() {
        signUpRequest = new SignUpRequest("username", "nickname", "password", "password","email@test.com");
        signInRequest = new SignInRequest("nickname", "password");
        jwtToken = JwtToken.builder()
                .accessToken("accessToken")
                .refreshToken("refreshToken")
                .build();
    }

    @Test
    public void signUpTest() throws Exception {

        // given
        doNothing().when(authService).signUp(signUpRequest);

        // when, then
        mockMvc.perform(post("/api/auth/signUp")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signUpRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("회원가입 성공"));
    }

    @Test
    public void signInTest() throws Exception {

        // given
        when(authService.signIn(signInRequest)).thenReturn(jwtToken);

        // when, then
        mockMvc.perform(post("/api/auth/signIn")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signInRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("로그인 성공"));
//                .andExpect(jsonPath("$.data.accessToken").value("accessToken"))
//                .andExpect(jsonPath("$.data.refreshToken").value("refreshToken"));
    }
}
