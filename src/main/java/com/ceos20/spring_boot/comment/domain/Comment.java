package com.ceos20.spring_boot.comment.domain;

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
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long commentId;

    @Column(name = "comment_content", nullable = false, length = 200)
    private String commentContent;

    @Column(name = "created_at", nullable = false)
    private Timestamp createdAt;

    @Column(name = "post_id", nullable = false)
    private Long postId;

    @Column(name = "like_num", nullable = false)
    private int likeNum;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_comment_id")
    private Comment parentCommentId;


}
