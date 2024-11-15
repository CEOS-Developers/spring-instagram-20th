package com.ceos20.spring_instagram.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RegisterResponseDTO {
    private Long memberId;
    private String membername;
    private String email;
    private String profilePicture; // 프로필 사진 (필요 시 추가)

    // MemberDTO를 RegisterResponseDTO로 변환
    public static RegisterResponseDTO from(MemberDTO memberDTO) {
        return RegisterResponseDTO.builder()
                .memberId(memberDTO.getMemberId())
                .membername(memberDTO.getMembername())
                .email(memberDTO.getEmail())
                .profilePicture(memberDTO.getProfilePicture())
                .build();
    }
}
