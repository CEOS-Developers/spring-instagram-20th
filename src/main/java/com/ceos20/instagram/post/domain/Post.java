package com.ceos20.instagram.post.domain;

import com.ceos20.instagram.user.domain.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;

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

    // 게시글 본문
    private String content;

    // 게시글 생성 시간
    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();


    // 게시글 작성자
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", nullable = false)
    private User userId;

//    // 게시글 좋아요 수 (기본 값: 0)
//    private Long likeCount = 0L;
//
//    // 게시글 댓글 수 (기본 값: 0)
//    private Long commentCount= 0L;
}
