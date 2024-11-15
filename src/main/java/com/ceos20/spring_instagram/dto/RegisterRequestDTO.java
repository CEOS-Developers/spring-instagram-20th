// RegisterRequestDTO.java
package com.ceos20.spring_instagram.dto;

import lombok.Getter;

@Getter
public class RegisterRequestDTO {
    private String membername;
    private String email;
    private String password;
    private String profilePicture;
}
