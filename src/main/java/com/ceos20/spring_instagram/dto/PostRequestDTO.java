package com.ceos20.spring_instagram.dto;

import lombok.Getter;

@Getter
public class PostRequestDTO {

    private final String image;
    private final String caption;

    private PostRequestDTO(String image, String caption) {
        this.image = image;
        this.caption = caption;
    }

    // 정적 팩토리 메서드
    public static PostRequestDTO of(String image, String caption) {
        return new PostRequestDTO(image, caption);
    }
}
