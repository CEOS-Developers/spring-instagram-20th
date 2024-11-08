package com.ceos20.spring_boot.post.dto;

import com.ceos20.spring_boot.post.domain.PostImage;
import lombok.Getter;

@Getter
public class PostImageResponseDto {
    private Long imageId;
    private String fileRoot;
    private Long postId;

    private PostImageResponseDto(PostImage postImage) {
        this.imageId = postImage.getImageId();
        this.fileRoot = postImage.getFileRoot();
        this.postId = postImage.getPostId().getPostId();
    }

    // 정적 팩토리 메서드
    public static PostImageResponseDto fromEntity(PostImage postImage) {
        return new PostImageResponseDto(postImage);
    }
}
