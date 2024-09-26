package com.ceos20.instagram.post.domain;

import com.ceos20.instagram.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostHashtag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_hashtag_id")
    private Long id;

    // 해시태그가 사용된 게시글
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="post_id", nullable = false)
    private Post postId;

    // 사용된 해시태그
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="hashtag_id", nullable = false)
    private Hashtag hashtagId;
}
