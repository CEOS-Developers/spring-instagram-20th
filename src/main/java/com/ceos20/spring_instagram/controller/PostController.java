package com.ceos20.spring_instagram.controller;

import com.ceos20.spring_instagram.domain.Post;
import com.ceos20.spring_instagram.service.PostService;
import com.ceos20.spring_instagram.jwt.TokenProvider;
import com.ceos20.spring_instagram.dto.PostRequestDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;
    private final TokenProvider tokenProvider;

    public PostController(PostService postService, TokenProvider tokenProvider) {
        this.postService = postService;
        this.tokenProvider = tokenProvider;
    }

    // 게시글 작성 API
    @PostMapping("/create")
    public ResponseEntity<Post> createPost(@RequestBody PostRequestDTO postRequestDTO, @RequestHeader("Authorization") String token) {
        // 토큰에서 memberId 추출
        Long memberId = getMemberIdFromToken(token);

        if (memberId == null) {
            return ResponseEntity.status(403).body(null);  // 토큰이 없거나 잘못된 경우 403 반환
        }

        // 게시글 생성
        Post createdPost = postService.createPost(memberId, postRequestDTO.getImage(), postRequestDTO.getCaption());
        return ResponseEntity.status(201).body(createdPost);
    }

    // 토큰에서 사용자 ID 추출
    private Long getMemberIdFromToken(String token) {
        if (token == null || !token.startsWith("Bearer ")) {
            return null;  // Bearer가 없는 경우 null 반환
        }

        try {
            String jwtToken = token.replace("Bearer ", "");  // 'Bearer ' 부분 제거
            Authentication authentication = tokenProvider.getAuthentication(jwtToken);
            return Long.parseLong(authentication.getName());
        } catch (Exception e) {
            return null;  // 토큰이 잘못되었거나 유효하지 않은 경우 null 반환
        }
    }
}


