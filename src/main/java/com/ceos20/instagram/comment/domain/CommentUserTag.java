package com.ceos20.instagram.comment.domain;

import com.ceos20.instagram.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentUserTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_user_tag")
    private Long id;

    // 댓글
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="comment_id", nullable = false)
    private Comment commentId;

    // 태그된 회원
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", nullable = false)
    private User userId;

}
