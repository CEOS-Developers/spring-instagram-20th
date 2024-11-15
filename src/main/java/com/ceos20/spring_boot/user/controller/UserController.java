package com.ceos20.spring_boot.user.controller;

import com.ceos20.spring_boot.user.DTO.UserCreateDTO;
import com.ceos20.spring_boot.user.DTO.UserDTO;
import com.ceos20.spring_boot.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 회원가입
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserCreateDTO userCreateDTO) {
        userService.registerUser(userCreateDTO);
        return ResponseEntity.ok("User registered successfully");
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestParam String username, @RequestParam String password) {
        String token = userService.authenticateUser(username, password);
        return ResponseEntity.ok("Bearer " + token);
    }
}
