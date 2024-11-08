package com.ceos20.instagram.post.dto;

import com.ceos20.instagram.post.domain.Post;
import com.ceos20.instagram.post.domain.PostImage;
import com.ceos20.instagram.user.domain.User;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class PostRequestDto {
    private String content;
    private String userId;
    private LocalDateTime createdAt;
    private List<MultipartFile> images;

    public Post toEntity(User writer) {
        return Post.builder()
                .content(this.content)
                .writer(writer)
                .createdAt(this.createdAt)
                .build();
    }

}