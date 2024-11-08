package com.ceos20.spring_boot.post.dto;

import com.ceos20.spring_boot.post.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostRequestDto {
    private String postContent;
    private int likeNum;
    private int commentNum;
    private Long userId;

    public static Post toEntity(PostRequestDto dto) {
        return Post.builder()
                .postContent(dto.getPostContent())
                .likeNum(dto.getLikeNum())
                .commentNum(dto.getCommentNum())
                .build();
    }
}
