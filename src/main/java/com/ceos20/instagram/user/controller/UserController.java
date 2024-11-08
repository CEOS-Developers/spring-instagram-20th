package com.ceos20.instagram.user.controller;

import com.ceos20.instagram.user.dto.UserPageResponseDto;
import com.ceos20.instagram.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
