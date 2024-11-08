package com.ceos20.instagram.comment.dto;

import com.ceos20.instagram.comment.domain.Comment;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class CommentsResponseDto {
    private String comment;
    private String user;
    private LocalDateTime date;
    private Long likeCount;

    public static CommentsResponseDto from(final Comment comment) {
        return CommentsResponseDto.builder()
                .comment(comment.getContent())
                .user(comment.getUser().getNickname())
                .likeCount(comment.getLikeCount())
                .date(comment.getCreatedAt())
                .build();
    }
}
