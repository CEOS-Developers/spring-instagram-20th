package com.ceos20.spring_boot.post.dto;

import com.ceos20.spring_boot.post.domain.Post;
import com.ceos20.spring_boot.post.domain.PostLike;
import com.ceos20.spring_boot.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostLikeRequestDto {
    private Long postId;
    private Long userId;

    // 정적 팩토리 메서드를 사용하여 PostLike 엔티티로 변환
    public static PostLike toEntity(PostLikeRequestDto dto, Post post, User user) {
        return PostLike.builder()
                .postId(post)
                .userId(user)
                .build();
    }
}
