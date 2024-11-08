package com.ceos20.spring_boot.comment.dto;

import com.ceos20.spring_boot.comment.domain.Comment;
import com.ceos20.spring_boot.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentRequestDto {
    private String commentContent;
    private Timestamp createdAt;
    private Long postId;
    private int likeNum;
    private Long userId;
    private Long parentCommentId;

    // 정적 팩토리 메서드를 사용해 Comment 엔티티로 변환
    public static Comment toEntity(CommentRequestDto dto, User user, Comment parentComment) {
        return Comment.builder()
                .commentContent(dto.getCommentContent())
                .createdAt(dto.getCreatedAt())
                .postId(dto.getPostId())
                .likeNum(dto.getLikeNum())
                .userId(user)
                .parentCommentId(parentComment)
                .build();
    }
}
