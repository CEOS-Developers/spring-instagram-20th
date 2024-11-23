package com.ceos20.instagram.domain.follow.controller;

import com.ceos20.instagram.global.common.response.ApiResponse;
import com.ceos20.instagram.global.common.response.ResponseBuilder;
import com.ceos20.instagram.domain.follow.dto.GetFollowerResponse;
import com.ceos20.instagram.domain.follow.dto.GetFollowingResponse;
import com.ceos20.instagram.domain.follow.service.FollowService;
import com.ceos20.instagram.global.common.annotation.Login;
import com.ceos20.instagram.domain.member.domain.Member;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/follow")
public class FollowController {

    private final FollowService followService;

    @PostMapping("/friend/{friendId}")
    public ResponseEntity<ApiResponse<Void>> createFollow(
            @PathVariable Long friendId,
            @Login Member member) {

        followService.createFollow(member, friendId);
        return ResponseBuilder.createApiResponse("팔로우 완료", null);
    }

    @GetMapping("/follower")
    public ResponseEntity<ApiResponse<List<GetFollowerResponse>>> getFollowers(
            @RequestParam(value = "memberId", required = false) Long memberId,
            @Login Member member) {

        List<GetFollowerResponse> getFollowerResponses = followService.getFollower(memberId, member);
        return ResponseBuilder.createApiResponse("나의 팔로워 조회 완료", getFollowerResponses);
    }

    @GetMapping("/following")
    public ResponseEntity<ApiResponse<List<GetFollowingResponse>>> getFollowings(
            @RequestParam(value = "memberId", required = false) Long memberId,
            @Login Member member) {

        List<GetFollowingResponse> getFollowingResponses = followService.getFollowing(memberId, member);
        return ResponseBuilder.createApiResponse("나의 팔로잉 조회 완료", getFollowingResponses);
    }

    @DeleteMapping("/friend/{friendId}")
    public ResponseEntity<ApiResponse<Void>> deleteFollow(
            @PathVariable Long friendId,
            @Login Member member) {

        followService.deleteFollow(member, friendId);
        return ResponseBuilder.createApiResponse("팔로우 끊기 완료", null);
    }
}
