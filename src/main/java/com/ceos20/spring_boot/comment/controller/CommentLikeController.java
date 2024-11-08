package com.ceos20.spring_boot.comment.controller;

import com.ceos20.spring_boot.comment.dto.CommentLikeRequestDto;
import com.ceos20.spring_boot.comment.dto.CommentLikeResponseDto;
import com.ceos20.spring_boot.comment.service.CommentLikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comment-likes")
public class CommentLikeController {

    private final CommentLikeService commentLikeService;

    @Autowired
    public CommentLikeController(CommentLikeService commentLikeService) {
        this.commentLikeService = commentLikeService;
    }

    // 댓글 좋아요 추가 (POST 요청)
    @PostMapping
    public ResponseEntity<CommentLikeResponseDto> addCommentLike(@RequestBody CommentLikeRequestDto commentLikeRequestDto) {
        CommentLikeResponseDto createdCommentLike = commentLikeService.addCommentLike(commentLikeRequestDto);
        return ResponseEntity.ok(createdCommentLike);
    }

    // 댓글 좋아요 삭제 (DELETE 요청)
    @DeleteMapping("/{commentLikeId}")
    public ResponseEntity<Void> deleteCommentLike(@PathVariable Long commentLikeId) {
        commentLikeService.deleteCommentLike(commentLikeId);
        return ResponseEntity.noContent().build();
    }

    // 댓글 좋아요 조회 (GET 요청)
    @GetMapping("/{commentLikeId}")
    public ResponseEntity<CommentLikeResponseDto> getCommentLikeById(@PathVariable Long commentLikeId) {
        CommentLikeResponseDto commentLike = commentLikeService.getCommentLikeById(commentLikeId);
        return ResponseEntity.ok(commentLike);
    }
}
