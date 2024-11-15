package com.ceos20.spring_instagram.dto;

import com.ceos20.spring_instagram.domain.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberDTO {
    private Long memberId;
    private String membername;
    private String email;
    private String profilePicture;


    public static MemberDTO from(Member member) {
        return MemberDTO.builder()
                .memberId(member.getMemberId())
                .membername(member.getMembername())
                .email(member.getEmail())
                .profilePicture(member.getProfilePicture())
                .build();
    }
}
