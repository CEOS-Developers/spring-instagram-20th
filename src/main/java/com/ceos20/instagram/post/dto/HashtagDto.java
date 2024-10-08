package com.ceos20.instagram.post.dto;

import com.ceos20.instagram.post.domain.Hashtag;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class HashtagDto {
    private Long id;
    private String tag;

    public Hashtag toEntity() {
        return Hashtag.builder()
                .tag(tag)
                .build();
    }
}
