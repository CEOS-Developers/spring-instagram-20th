package com.ceos20.instagram.post.domain;

import com.ceos20.instagram.image.domain.Image;
import com.ceos20.instagram.post.dto.CreatePostRequest;
import com.ceos20.instagram.member.domain.Member;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    private String content;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private Member author;

    @Builder.Default
    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<Image> images = new ArrayList<>();

    public static Post toEntity(CreatePostRequest createPostRequest, Member member) {
        return Post.builder()
                .content(createPostRequest.getContent())
                .author(member)
                .build();
    }

    public void updateContent(String content) {
        this.content = content;
    }
}
