package com.ceos20.instagram.post.dto;

import com.ceos20.instagram.post.domain.Post;
import com.ceos20.instagram.post.domain.PostImage;
import com.ceos20.instagram.user.domain.User;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class PostResponseDto {
    private Long id;
    private String content;
    private User writer;
    private LocalDateTime createdAt;
    private Long likeCount;
    private Long commentCount;
    private List<PostImage> images;



}

