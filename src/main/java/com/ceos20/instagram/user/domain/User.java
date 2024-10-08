package com.ceos20.instagram.user.domain;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;



import java.time.LocalDate;
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
    @Size(min=1, max=30)
    @NotNull
    @Column(unique = true)
    private String nickname;

    // 비밀번호
    @NotNull
    private String password;

    // 이메일
    private String email;

    // 전화번호
    private String phone;

    // 소개글
    @Size(min=0, max=150)
    private String introduce;

    // 성별
    private String gender;

    // 프로필 이미지
    private String profileImage;

    // 사용자 실명
    private String name;

    // 팔로잉 수
    @Builder.Default
    @NotNull
    private Long followingCount = 0L;

    // 팔로워 수
    @Builder.Default
    @NotNull
    private Long followerCount = 0L;

    // 게시물 수
    @Builder.Default
    @NotNull
    private Long postCount = 0L;

    // 가입 일자
    @NotNull
    @Builder.Default
    private LocalDateTime registedAt = LocalDateTime.now();

    // 생일
    private LocalDate birth;

    public void increaseFollowingCount() {
        followingCount++;
    }

    public void decreaseFollowingCount() {
        followingCount--;
    }

    public void increaseFollowerCount() {
        followerCount++;
    }

    public void decreaseFollowerCount() {
        followerCount--;
    }

    public void increasePostCount() {
        postCount++;
    }

    public void decreasePostCount() {
        postCount--;
    }

}
