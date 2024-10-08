package com.ceos20.spring_instagram.service;

import com.ceos20.spring_instagram.domain.Member;
import com.ceos20.spring_instagram.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    // 회원 등록 로직
    public Member registerMember(Member member) {
        return memberRepository.save(member);
    }

    // 특정 회원을 membername으로 조회하는 로직
    public Optional<Member> findMemberByMembername(String membername) {
        return memberRepository.findByMembername(membername);
    }

    // 회원 업데이트 로직
    public Member updateMember(Member member) {
        return memberRepository.save(member);
    }
}
