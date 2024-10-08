package com.ceos20.spring_boot.user.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class UserDTO {
    @Getter
    @Setter
    private Long userId;
    @Getter
    @Setter
    private String username;
    @Getter
    @Setter
    private String password;
    @Getter
    @Setter
    private String gender;
    @Getter
    @Setter
    private LocalDateTime birthday;
    @Getter
    @Setter
    private String profileImage;
    @Getter
    @Setter
    private Long uploadPost;
    @Getter
    @Setter
    private Long followNum;
    @Getter
    @Setter
    private Long followingNum;

    public UserDTO() {}

    public UserDTO(Long userId, String username, String password, String gender, LocalDateTime birthday, String profileImage, Long uploadPost, Long followNum, Long followingNum) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.gender = gender;
        this.birthday = birthday;
        this.profileImage = profileImage;
        this.uploadPost = uploadPost;
        this.followNum = followNum;
        this.followingNum = followingNum;
    }

}
