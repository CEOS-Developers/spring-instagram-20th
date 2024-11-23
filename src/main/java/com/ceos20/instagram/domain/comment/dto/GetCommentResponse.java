package com.ceos20.instagram.domain.comment.dto;

import com.ceos20.instagram.domain.comment.domain.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetCommentResponse {

    private Long commentId;

    private Long parentCommentId;

    private String content;

    private Long authorId;

    private String authorNickname;

    public static GetCommentResponse fromEntity(Comment comment) {

        Long parentCommentId = null;
        if (comment.getParentComment() != null) {
            parentCommentId = comment.getParentComment().getId();
        }

        return GetCommentResponse.builder()
                .commentId(comment.getId())
                .parentCommentId(parentCommentId)
                .content(comment.getContent())
                .authorId(comment.getAuthor().getId())
                .authorNickname(comment.getAuthor().getNickname())
                .build();
    }
}
