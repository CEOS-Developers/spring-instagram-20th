package com.ceos20.spring_boot.post.dto;

import com.ceos20.spring_boot.post.domain.Post;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
public class PostResponseDto {
    private Long postId;
    private String postContent;
    private Timestamp createdAt;
    private int likeNum;
    private int commentNum;
    private Long userId;

    private PostResponseDto(Post post) {
        this.postId = post.getPostId();
        this.postContent = post.getPostContent();
        this.createdAt = post.getCreatedAt();
        this.likeNum = post.getLikeNum();
        this.commentNum = post.getCommentNum();
        this.userId = post.getUserId().getUserId();
    }

    // 정적 팩토리 메서드를 이용해 PostResponseDto로 변환
    public static PostResponseDto fromEntity(Post post) {
        return new PostResponseDto(post);
    }
}
