package com.ceos20.instagram.post.dto;

import com.ceos20.instagram.post.domain.Post;
import com.ceos20.instagram.post.domain.PostLike;
import com.ceos20.instagram.user.domain.User;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class PostLikeDto {
    private Post post;
    private User user;

    public PostLike toEntity(User user, Post post) {
        return PostLike.builder()
                .post(post)
                .user(user)
                .build();
    }
}
