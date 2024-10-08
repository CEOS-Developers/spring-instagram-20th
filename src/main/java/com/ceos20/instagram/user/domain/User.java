package com.ceos20.instagram.user.domain;

import jakarta.persistence.*;
import lombok.*;
import org.jetbrains.annotations.NotNull;

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

    private String username;      // ex) 최서지

    @NotNull
    @Column(unique = true)
    private String nickname;      // ex) @__seojii

    @NotNull
    private String password;

    private String email;

    private String imageUrl;


    // user 정보 수정
    public void updateInfo(String username, String nickname, String password, String email, String imageUrl) {
        this.username = username;
        this.nickname = nickname;
        this.password = password;
        this.email = email;
        this.imageUrl = imageUrl;
    }
}
