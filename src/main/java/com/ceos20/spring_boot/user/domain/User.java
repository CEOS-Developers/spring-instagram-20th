package com.ceos20.spring_boot.user.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "gender", length = 10)
    private String gender;

    @Column(name = "birthday")
    private LocalDateTime birthday;

    @Column(name = "profile_image")
    private String profileImage;

    @Column(name = "upload_post")
    private Long uploadPost;

    @Column(name = "follower_num")
    private Long followerNum;

    @Column(name = "following_num")
    private Long followingNum;
}
