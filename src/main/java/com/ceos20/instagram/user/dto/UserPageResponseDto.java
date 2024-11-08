package com.ceos20.instagram.user.dto;

import com.ceos20.instagram.post.domain.Post;
import com.ceos20.instagram.user.domain.User;
import lombok.*;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class UserPageResponseDto {
    private Long id;
    private String nickname;
    private String profileImage;
    private String name;
    private String introduce;
    private Long followers;
    private Long following;
    private Long postCount;
    private List<Post> posts;

    public static UserPageResponseDto of(User user, List<Post> posts) {
        return UserPageResponseDto.builder()
                .id(user.getId())
                .nickname(user.getNickname())
                .profileImage(user.getProfileImage())
                .name(user.getName())
                .introduce(user.getIntroduce())
                .followers(user.getFollowerCount())
                .following(user.getFollowingCount())
                .postCount(user.getPostCount())
                .posts(posts)
                .build();
    }

}
