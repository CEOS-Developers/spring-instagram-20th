package com.ceos20.instagram.domain.comment.controller;

import com.ceos20.instagram.domain.comment.dto.CreateCommentRequest;
import com.ceos20.instagram.domain.comment.dto.GetCommentResponse;
import com.ceos20.instagram.domain.comment.dto.UpdateCommentRequest;
import com.ceos20.instagram.domain.comment.service.CommentService;
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
@RequestMapping("/api/comment")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/post/{postId}")
    public ResponseEntity<ApiResponse<Void>> createComment(
            @RequestBody CreateCommentRequest createCommentRequest,
            @PathVariable Long postId,
            @Login Member member) {

        commentService.createComment(createCommentRequest, member, postId);
        return ResponseBuilder.createApiResponse("댓글 작성 완료", null);
    }

    @PostMapping("/{commentId}/reply")
    public ResponseEntity<ApiResponse<Void>> createChileComment(
            @RequestBody CreateCommentRequest createCommentRequest,
            @PathVariable Long commentId,
            @Login Member member) {

        commentService.createChildComment(createCommentRequest, member, commentId);
        return ResponseBuilder.createApiResponse("대댓글 작성 완료", null);
    }

    @PatchMapping("/{commentId}")
    public ResponseEntity<ApiResponse<Void>> updateContentById(
            @RequestBody UpdateCommentRequest updateCommentRequest,
            @PathVariable Long commentId) {

        commentService.updateContent(updateCommentRequest, commentId);
        return ResponseBuilder.createApiResponse("댓글 수정 완료", null);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<ApiResponse<Void>> deleteCommentById(@PathVariable Long commentId) {

        commentService.deleteComment(commentId);
        return ResponseBuilder.createApiResponse("댓글 삭제 완료", null);
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<ApiResponse<List<GetCommentResponse>>> getAllCommentsByPostId(@PathVariable Long postId) {

        List<GetCommentResponse> getCommentResponses = commentService.getAllCommentsByPostId(postId);
        return ResponseBuilder.createApiResponse("게시글의 모든 댓글 조회 완료", getCommentResponses);
    }
}
