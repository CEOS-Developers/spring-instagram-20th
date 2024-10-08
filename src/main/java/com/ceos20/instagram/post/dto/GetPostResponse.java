package com.ceos20.instagram.post.dto;

import com.ceos20.instagram.post.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetPostResponse {

    private String content;

    private List<Image> imageList;

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Image {

        private String imageUrl;
        private Long index;
    }

    public static GetPostResponse fromEntity(Post post) {
        List<Image> images = post.getImages().stream()
                .map(image -> new Image(image.getImageUrl(), image.getIndex()))
                .collect(Collectors.toList());

        return GetPostResponse.builder()
                .content(post.getContent())
                .imageList(images)
                .build();
    }
}
