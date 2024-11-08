package com.ceos20.spring_boot.comment.dto;

import com.ceos20.spring_boot.comment.domain.Comment;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
public class CommentResponseDto {
    private Long commentId;
    private String commentContent;
    private Timestamp createdAt;
    private Long postId;
    private int likeNum;
    private Long userId;
    private Long parentCommentId;

    private CommentResponseDto(Comment comment) {
        this.commentId = comment.getCommentId();
        this.commentContent = comment.getCommentContent();
        this.createdAt = comment.getCreatedAt();
        this.postId = comment.getPostId();
        this.likeNum = comment.getLikeNum();
        this.userId = comment.getUserId().getUserId();
        this.parentCommentId = comment.getParentCommentId() != null ? comment.getParentCommentId().getCommentId() : null;
    }

    // 정적 팩토리 메서드를 사용해 CommentResponseDto 생성
    public static CommentResponseDto fromEntity(Comment comment) {
        return new CommentResponseDto(comment);
    }
}
