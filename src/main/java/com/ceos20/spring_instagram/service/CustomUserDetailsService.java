package com.ceos20.spring_instagram.service;

import com.ceos20.spring_instagram.domain.Member;
import com.ceos20.spring_instagram.repository.MemberRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    public CustomUserDetailsService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String memberId) {
        // ID로 사용자를 조회
        Member member = memberRepository.findById(Long.valueOf(memberId)).orElseThrow(() -> new RuntimeException("User not found"));

        return User.builder()
                .username(member.getMembername())
                .password(member.getPassword())
                .authorities("USER") // 기본적으로 "USER" 권한 부여
                .build();
    }
}
