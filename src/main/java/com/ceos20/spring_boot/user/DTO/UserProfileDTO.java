package com.ceos20.spring_boot.user.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

public class UserProfileDTO {

    @Getter
    @Setter
    private Long userId;
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private String profileImage;
    @Getter
    @Setter
    private Long uploadPost;
    @Getter
    @Setter
    private Long followerNum;
    @Getter
    @Setter
    private Long followingNum;

    // 기본 생성자
    public UserProfileDTO() {}

    // 모든 필드를 포함하는 생성자
    public UserProfileDTO(Long userId, String name, String profileImage, Long uploadPost, Long followerNum, Long followingNum) {
        this.userId = userId;
        this.name = name;
        this.profileImage = profileImage;
        this.uploadPost = uploadPost;
        this.followerNum = followerNum;
        this.followingNum = followingNum;
    }

}
