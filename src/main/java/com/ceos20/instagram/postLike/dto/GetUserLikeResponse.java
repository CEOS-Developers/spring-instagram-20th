package com.ceos20.instagram.postLike.dto;

import com.ceos20.instagram.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetUserLikeResponse {

    private String nickname;

    public static GetUserLikeResponse fromEntity(Member member) {
        return GetUserLikeResponse.builder()
                .nickname(member.getNickname())
                .build();
    }
}
