package com.ceos20.spring_boot.comment.dto;

import com.ceos20.spring_boot.comment.domain.Comment;
import com.ceos20.spring_boot.comment.domain.CommentLike;
import com.ceos20.spring_boot.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentLikeRequestDto {
    private Long userId;
    private Long commentId;

    // 정적 팩토리 메서드를 사용해 CommentLike 엔티티로 변환
    public static CommentLike toEntity(CommentLikeRequestDto dto, User user, Comment comment) {
        return CommentLike.builder()
                .user(user)
                .comment(comment)
                .build();
    }
}
