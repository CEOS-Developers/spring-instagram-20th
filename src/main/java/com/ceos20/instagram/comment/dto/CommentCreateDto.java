package com.ceos20.instagram.comment.dto;

import com.ceos20.instagram.comment.domain.Comment;
import com.ceos20.instagram.post.domain.Post;
import com.ceos20.instagram.user.domain.User;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class CommentCreateDto {
    private String content;
    private User user;
    private Post post;

    public Comment toEntity(User user, Post post) {
        return Comment.builder()
                .content(content)
                .user(user)
                .post(post)
                .build();
    }
}
