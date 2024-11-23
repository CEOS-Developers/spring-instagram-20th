package com.ceos20.instagram.domain.member.dto;

import com.ceos20.instagram.domain.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetMemberInfoResponse {

    private String username;

    private String nickname;

    private String email;

    private String imageUrl;

    public static GetMemberInfoResponse fromEntity(Member member) {
        return GetMemberInfoResponse.builder()
                .username(member.getUsername())
                .nickname(member.getNickname())
                .email(member.getEmail())
                .imageUrl(member.getImageUrl())
                .build();
    }
}
