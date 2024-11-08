package com.ceos20.spring_boot.comment.dto;

import com.ceos20.spring_boot.comment.domain.CommentLike;
import lombok.Getter;

@Getter
public class CommentLikeResponseDto {
    private Long commentLikeId;
    private Long userId;
    private Long commentId;

    private CommentLikeResponseDto(CommentLike commentLike) {
        this.commentLikeId = commentLike.getCommentLikeId();
        this.userId = commentLike.getUser().getUserId();
        this.commentId = commentLike.getComment().getCommentId();
    }

    // 정적 팩토리 메서드를 사용해 CommentLikeResponseDto 생성
    public static CommentLikeResponseDto fromEntity(CommentLike commentLike) {
        return new CommentLikeResponseDto(commentLike);
    }
}
