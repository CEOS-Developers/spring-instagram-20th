package com.ceos20.instagram.post.dto;

import com.ceos20.instagram.post.domain.Post;
import com.ceos20.instagram.post.domain.PostImage;
import com.ceos20.instagram.user.domain.User;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class PostCreateDto {
    private String content;
    private User writer;
    private List<PostImage> images;

    public Post toEntity(String content, User writer) {
        return Post.builder()
                .content(content)
                .writer(writer)
                .build();
    }
}
