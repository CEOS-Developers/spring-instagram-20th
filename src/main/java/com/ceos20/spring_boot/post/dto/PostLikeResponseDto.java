package com.ceos20.spring_boot.post.dto;

import com.ceos20.spring_boot.post.domain.PostLike;
import lombok.Getter;

@Getter
public class PostLikeResponseDto {
    private Long likeId;
    private Long postId;
    private Long userId;

    private PostLikeResponseDto(PostLike postLike) {
        this.likeId = postLike.getLikeId();
        this.postId = postLike.getPostId().getPostId();
        this.userId = postLike.getUserId().getUserId();
    }

    // 정적 팩토리 메서드를 사용하여 PostLikeResponseDto 생성
    public static PostLikeResponseDto fromEntity(PostLike postLike) {
        return new PostLikeResponseDto(postLike);
    }
}
