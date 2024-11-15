package com.ceos20.spring_boot.user.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class UserDTO {

    private Long userId;
    private String username;
    private String password;
    private String gender;
    private LocalDateTime birthday;
    private String profileImage;
    private Long uploadPost;
    private Long followNum;
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
