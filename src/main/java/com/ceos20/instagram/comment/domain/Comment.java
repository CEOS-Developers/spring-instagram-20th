package com.ceos20.instagram.comment.domain;

import com.ceos20.instagram.post.domain.Post;
import com.ceos20.instagram.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    // 내용
    @Column(nullable = false)
    private String content;

    // 작성일
    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    // 댓글 작성자
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", nullable = false)
    private User userId;

    // 댓글 단 게시글
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="post_id")
    private Post postId;

    // 부모 댓글
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="parent_id")
    private Comment commentId;

//    // 좋아요 수
//    @Column(nullable = false)
//    private Long likeCount = 0L;

}
