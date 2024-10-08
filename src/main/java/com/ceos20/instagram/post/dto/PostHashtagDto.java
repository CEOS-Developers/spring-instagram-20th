package com.ceos20.instagram.post.dto;

import com.ceos20.instagram.post.domain.Hashtag;
import com.ceos20.instagram.post.domain.Post;
import com.ceos20.instagram.post.domain.PostHashtag;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class PostHashtagDto {
    private Post post;
    private Hashtag hashtag;

    public PostHashtag toEntity() {
        return PostHashtag.builder()
                .post(post)
                .hashtag(hashtag)
                .build();
    }
}
