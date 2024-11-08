package com.ceos20.instagram.member.service;

import com.ceos20.instagram.global.exception.ExceptionCode;
import com.ceos20.instagram.global.exception.NotFoundException;
import com.ceos20.instagram.member.domain.Member;
import com.ceos20.instagram.member.dto.GetMemberInfoResponse;
import com.ceos20.instagram.member.dto.SaveMemberRequest;
import com.ceos20.instagram.member.dto.UpdateMemberInfoRequest;
import com.ceos20.instagram.member.repository.MemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Long saveUser(SaveMemberRequest saveMemberRequest) {

        Member member = saveMemberRequest.toEntity();
        return memberRepository.save(member).getId();
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
}
