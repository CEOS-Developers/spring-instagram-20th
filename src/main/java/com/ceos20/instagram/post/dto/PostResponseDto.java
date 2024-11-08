package com.ceos20.instagram.post.dto;

import com.ceos20.instagram.comment.domain.Comment;
import com.ceos20.instagram.post.domain.Post;
import com.ceos20.instagram.post.domain.PostImage;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class PostResponseDto {
    private String content;
    private String nickname;
    private LocalDateTime createdAt;
    private Long likeCount;
    private Long commentCount;
    private List<String> imageUrls;

    public static PostResponseDto of(final Post post, List<String> imageUrls) {
        return PostResponseDto.builder()
                .content(post.getContent())
                .nickname(post.getNickname())
                .createdAt(post.getCreatedAt())
                .likeCount(post.getLikeCount())
                .commentCount(post.getCommentCount())
                .imageUrls(imageUrls)
                .build();
    }
}
