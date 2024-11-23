package com.ceos20.instagram.domain.postLike.domain;

import com.ceos20.instagram.domain.post.domain.Post;
import com.ceos20.instagram.domain.member.domain.Member;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    public static PostLike toEntity(Member member, Post post) {
        return PostLike.builder()
                .member(member)
                .post(post)
                .build();
    }
}
