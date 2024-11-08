package com.ceos20.spring_instagram.dto;

import com.ceos20.spring_instagram.domain.Comment;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class CommentDTO {
    private Long commentId;
    private Long postId;
    private Long memberId;
    private String text;
    private LocalDateTime timestamp;

    // 정적 팩토리 메서드
    public static CommentDTO from(Comment comment) {
        return CommentDTO.builder()
                .commentId(comment.getCommentId())
                .postId(comment.getPost().getPostId())
                .memberId(comment.getMember().getMemberId())
                .text(comment.getText())
                .timestamp(comment.getTimestamp())
                .build();
    }
}
