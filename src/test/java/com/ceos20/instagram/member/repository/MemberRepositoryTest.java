package com.ceos20.instagram.member.repository;

import com.ceos20.instagram.domain.member.domain.Member;
import com.ceos20.instagram.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
public class MemberRepositoryTest {

    private final MemberRepository memberRepository;

    @Autowired
    public MemberRepositoryTest(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Test
    @DisplayName("id로 유저 조회 테스트")
    void findByIdTest() throws Exception {
        // given
        Member member = Member.builder()
                .username("최서지")
                .nickname("seoji")
                .password("password")
                .build();

        // when
        memberRepository.save(member);
        Optional<Member> findUser = memberRepository.findById(member.getId());

        // then
        assertEquals(member.getId(), findUser.get().getId());
    }
}
