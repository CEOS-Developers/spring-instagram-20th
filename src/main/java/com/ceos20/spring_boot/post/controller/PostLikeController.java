package com.ceos20.spring_boot.post.controller;

import com.ceos20.spring_boot.post.domain.PostLike;
import com.ceos20.spring_boot.post.service.PostLikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/postlikes")
public class PostLikeController {

    private final PostLikeService postLikeService;

    @Autowired
    public PostLikeController(PostLikeService postLikeService) {
        this.postLikeService = postLikeService;
    }

    // 게시물 좋아요 추가 (POST 요청)
    @PostMapping
    public ResponseEntity<PostLike> addPostLike(@RequestBody PostLike postLike) {
        PostLike createdPostLike = postLikeService.addPostLike(postLike);
        return ResponseEntity.ok(createdPostLike);
    }

    // 게시물 좋아요 삭제 (DELETE 요청)
    @DeleteMapping("/{postLikeId}")
    public ResponseEntity<Void> deletePostLike(@PathVariable Long postLikeId) {
        postLikeService.deletePostLike(postLikeId);
        return ResponseEntity.noContent().build();
    }

    // 특정 게시물의 좋아요 수 조회 (GET 요청)
    @GetMapping("/count/{postId}")
    public ResponseEntity<Long> countPostLikesByPostId(@PathVariable Long postId) {
        Long likeCount = postLikeService.countPostLikesByPostId(postId);
        return ResponseEntity.ok(likeCount);
    }
}
