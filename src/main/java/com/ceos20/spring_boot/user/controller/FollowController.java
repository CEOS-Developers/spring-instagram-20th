package com.ceos20.spring_boot.user.controller;

import com.ceos20.spring_boot.user.domain.Follow;
import com.ceos20.spring_boot.user.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/follow")
public class FollowController {

    private final FollowService followService;

    @Autowired
    public FollowController(FollowService followService) {
        this.followService = followService;
    }

    // 팔로우 추가 (POST 요청)
    @PostMapping
    public ResponseEntity<Follow> followUser(@RequestBody Follow follow) {
        Follow createdFollow = followService.followUser(follow);
        return ResponseEntity.ok(createdFollow);
    }

    // 팔로우 취소 (DELETE 요청)
    @DeleteMapping("/{followId}")
    public ResponseEntity<Void> unfollowUser(@PathVariable Long followId) {
        followService.unfollowUser(followId);
        return ResponseEntity.noContent().build();
    }

    // 팔로우 조회 (GET 요청)
    @GetMapping("/{followId}")
    public ResponseEntity<Follow> getFollowById(@PathVariable Long followId) {
        Follow follow = followService.getFollowById(followId);
        return ResponseEntity.ok(follow);
    }
}
