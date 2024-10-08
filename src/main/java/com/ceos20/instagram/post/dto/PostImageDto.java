package com.ceos20.instagram.post.dto;

import com.ceos20.instagram.post.domain.Post;
import com.ceos20.instagram.post.domain.PostImage;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class PostImageDto {
    private Long id;
    private String path;
    private Post post;


    public PostImage toEntity(Post post) {
        return PostImage.builder()
                .post(post)
                .build();
    }

}
