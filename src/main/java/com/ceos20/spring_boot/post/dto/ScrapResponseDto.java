package com.ceos20.spring_boot.post.dto;

import com.ceos20.spring_boot.post.domain.Scrap;
import lombok.Getter;

@Getter
public class ScrapResponseDto {
    private Long scrapId;
    private Long postId;
    private Long userId;

    private ScrapResponseDto(Scrap scrap) {
        this.scrapId = scrap.getScrapId();
        this.postId = scrap.getPost().getPostId();
        this.userId = scrap.getUser().getUserId();
    }

    // 정적 팩토리 메서드를 사용하여 ScrapResponseDto 생성
    public static ScrapResponseDto fromEntity(Scrap scrap) {
        return new ScrapResponseDto(scrap);
    }
}
