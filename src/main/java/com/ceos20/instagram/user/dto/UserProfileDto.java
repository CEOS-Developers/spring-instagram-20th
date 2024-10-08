package com.ceos20.instagram.user.dto;

import com.ceos20.instagram.post.domain.Post;
import com.ceos20.instagram.user.domain.User;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class UserProfileDto {
    private Long id;
    private String nickname;
    private String profileImage;
    private String name;

}