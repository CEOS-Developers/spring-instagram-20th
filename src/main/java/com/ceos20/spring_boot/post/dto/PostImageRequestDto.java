package com.ceos20.spring_boot.post.dto;

import com.ceos20.spring_boot.post.domain.PostImage;
import com.ceos20.spring_boot.post.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostImageRequestDto {
    private String fileRoot;
    private Long postId;

    // 정적 팩토리 메서드를 이용해 PostImage 엔티티로 변환
    public static PostImage toEntity(PostImageRequestDto dto, Post post) {
        return PostImage.builder()
                .fileRoot(dto.getFileRoot())
                .postId(post)
                .build();
    }
}
