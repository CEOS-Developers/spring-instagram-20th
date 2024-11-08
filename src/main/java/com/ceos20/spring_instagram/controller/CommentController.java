package com.ceos20.spring_instagram.controller;

import com.ceos20.spring_instagram.dto.CommentDTO;
import com.ceos20.spring_instagram.service.CommentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@Tag(name = "Comment API", description = "댓글 관련 API")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    // 1. 새로운 댓글 생성
    @PostMapping("/")
    public ResponseEntity<CommentDTO> createComment(
            @RequestParam Long postId,
            @RequestParam Long memberId,
            @RequestBody String text) {
        CommentDTO createdComment = commentService.addComment(postId, memberId, text);
        return ResponseEntity.status(201).body(createdComment);
    }

    // 2. 모든 댓글 가져오기 (특정 포스트에 대한 댓글)
    @GetMapping("/post/{postId}")
    public ResponseEntity<List<CommentDTO>> getAllCommentsByPost(@PathVariable Long postId) {
        List<CommentDTO> comments = commentService.getAllCommentsByPost(postId);
        return ResponseEntity.ok(comments);
    }

    // 3. 특정 댓글 가져오기
    @GetMapping("/{commentId}")
    public ResponseEntity<CommentDTO> getCommentById(@PathVariable Long commentId) {
        CommentDTO comment = commentService.getCommentById(commentId);
        return ResponseEntity.ok(comment);
    }

    // 4. 특정 댓글 삭제
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.noContent().build();
    }
}
