package com.ceos20.spring_boot.post.domain;

import com.ceos20.spring_boot.user.domain.User;
import jakarta.persistence.*;
import lombok.*;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long postId;

    @Column(name = "post_content", columnDefinition = "TEXT")
    private String postContent;

    @Column(name = "created_at", nullable = false)
    private Timestamp createdAt;

    @Column(name = "like_num", nullable = false)
    private int likeNum;

    @Column(name = "comment_num", nullable = false)
    private int commentNum;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User userId;
}
