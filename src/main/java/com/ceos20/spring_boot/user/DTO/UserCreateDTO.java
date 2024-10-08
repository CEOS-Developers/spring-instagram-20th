package com.ceos20.spring_boot.user.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

public class UserCreateDTO {
    @Getter
    @Setter
    private String name;
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

    public UserCreateDTO() {}

    public UserCreateDTO(String name, String password, String gender, LocalDateTime birthday, String profileImage) {
        this.name = name;
        this.password = password;
        this.gender = gender;
        this.birthday = birthday;
        this.profileImage = profileImage;
    }

}
