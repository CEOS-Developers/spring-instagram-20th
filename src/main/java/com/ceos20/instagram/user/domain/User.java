package com.ceos20.instagram.user.domain;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    // 사용자 이름
    @Column(length = 30, nullable = false, unique = true)
    private String nickname;

    // 비밀번호
    @Column(nullable = false)
    private String password;

    // 이메일
    private String email;

    // 전화번호
    private String phone;

    // 소개글
    @Column(length = 150, nullable = true)
    private String introduce;

    // 성별
    private String gender;

    // 프로필 이미지
    private String profileImage;

    // 사용자 실명
    private String name;

    // 팔로잉 수
    @Builder.Default
    @Column(nullable = false)
    private Long followingCount = 0L;

    // 팔로워 수
    @Builder.Default
    @Column(nullable = false)
    private Long followerCount = 0L;

    // 게시물 수
    @Builder.Default
    @Column(nullable = false)
    private Long postCount = 0L;

    // 가입 일자
    @Column(nullable = false)
    @Builder.Default
    private LocalDateTime registedAt = LocalDateTime.now();

    // 생일
    private LocalDateTime birth;


}
