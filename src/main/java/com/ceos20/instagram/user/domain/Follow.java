package com.ceos20.instagram.user.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "follow_id")
    private Long id;

    // 팔로우 한 시간
    @Column(nullable = false)
    private LocalDateTime createdAt;

    // 친한 치구 여부
    private Boolean isBestFriend = false;

    // 팔로우 한 유저
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="follower_id")
    private User userId;

    // 팔로우 당한 유저
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="following_id")
    private User followingId;


}
