package com.ceos20.instagram.domain.member.service;

import com.ceos20.instagram.domain.auth.dto.SignUpRequest;
import com.ceos20.instagram.domain.member.repository.MemberRepository;
import com.ceos20.instagram.global.exception.ExceptionCode;
import com.ceos20.instagram.global.exception.NotFoundException;
import com.ceos20.instagram.domain.member.domain.Member;
import com.ceos20.instagram.domain.member.dto.GetMemberInfoResponse;
import com.ceos20.instagram.domain.member.dto.UpdateMemberInfoRequest;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void saveMember(SignUpRequest signUpRequest) {

        Member member = signUpRequest.toEntity(passwordEncoder);
        member.setRoleMember();
        memberRepository.save(member);
    }

    @Transactional
    public void saveAdmin(SignUpRequest signUpRequest) {

        Member member = signUpRequest.toEntity(passwordEncoder);
        member.setRoleAdmin();
        memberRepository.save(member);
    }

    @Transactional
    public Long updateMemberInfo(UpdateMemberInfoRequest updateMemberInfoRequest, Member member) {

        member.updateInfo(
                updateMemberInfoRequest.getUsername(),
                updateMemberInfoRequest.getNickname(),
                updateMemberInfoRequest.getPassword(),
                updateMemberInfoRequest.getEmail(),
                updateMemberInfoRequest.getImageUrl()
                );
        memberRepository.save(member);

        return member.getId();
    }

    public GetMemberInfoResponse getMemberInfoById(Long memberId) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundException(ExceptionCode.NOT_FOUND_MEMBER));

        return GetMemberInfoResponse.fromEntity(member);
    }

    public Member getMemberByNickname(String nickname) {

        Member member = memberRepository.findByNickname(nickname)
                .orElseThrow(() -> new NotFoundException(ExceptionCode.NOT_FOUND_MEMBER));
        return member;
    }

    public Boolean nicknameAlreadyExists(String nickname) {
        return memberRepository.existsByNickname(nickname);
    }

    public Boolean emailAlreadyExists(String email) {
        return memberRepository.existsByEmail(email);
    }
}
