package com.ceos20.instagram.image.domain;

import com.ceos20.instagram.post.domain.Post;
import com.ceos20.instagram.post.dto.CreatePostRequest;
import jakarta.persistence.*;
import lombok.*;
import org.jetbrains.annotations.NotNull;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long id;

    @NotNull
    private String imageUrl;

    @NotNull
    private Long index;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    public static Image toEntity(Post post, CreatePostRequest.Image image) {
        return Image.builder()
                .imageUrl(image.getImageUrl())
                .index(image.getIndex())
                .post(post)
                .build();
    }
}
