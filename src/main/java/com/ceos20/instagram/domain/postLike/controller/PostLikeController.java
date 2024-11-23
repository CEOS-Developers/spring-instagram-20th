package com.ceos20.instagram.domain.postLike.controller;

import com.ceos20.instagram.domain.postLike.dto.GetUserLikeResponse;
import com.ceos20.instagram.domain.postLike.service.PostLikeService;
import com.ceos20.instagram.global.common.annotation.Login;
import com.ceos20.instagram.global.common.response.ApiResponse;
import com.ceos20.instagram.global.common.response.ResponseBuilder;
import com.ceos20.instagram.domain.member.domain.Member;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/postlike")
public class PostLikeController {

    private final PostLikeService postLikeService;

    @PostMapping("/{postId}")
    public ResponseEntity<ApiResponse<Void>> handlePostLike(
            @PathVariable Long postId,
            @Login Member member
            ) {

        postLikeService.handlePostLike(postId, member.getId());
        return ResponseBuilder.createApiResponse("좋아요 완료", null);
    }

    @GetMapping("/member/{postId}")
    public ResponseEntity<ApiResponse<List<GetUserLikeResponse>>> getUsersWhoLikePost(@PathVariable Long postId) {

        List<GetUserLikeResponse> getUserLikeResponses = postLikeService.getUsersWhoLikePost(postId);
        return ResponseBuilder.createApiResponse("게시글에 좋아요 누른 멤버 조회 완료", getUserLikeResponses);
    }
}
