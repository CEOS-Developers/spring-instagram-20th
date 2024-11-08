package com.ceos20.instagram.member.service;

import com.ceos20.instagram.member.domain.Member;
import com.ceos20.instagram.member.dto.GetMemberInfoResponse;
import com.ceos20.instagram.member.dto.SaveMemberRequest;
import com.ceos20.instagram.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class MemberServiceTest {

    @Mock  // userRepository를 mock으로 주입하므로 모의 동작을 설정해야 한다.
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberService memberService;

    private Member testMember;

    @Test
    @DisplayName("유저 저장 테스트")
    public void saveUserTest() {
        // given
        SaveMemberRequest saveMemberRequest = SaveMemberRequest.builder()
                .username("seoji")
                .nickname("__seoji")
                .email("seoji@naver.com")
                .password("password")
                .build();
        Member member = saveMemberRequest.toEntity();

        when(memberRepository.save(any(Member.class))).thenReturn(member);

        // when
        Long userId = memberService.saveUser(saveMemberRequest);

        // then
        verify(memberRepository, times(1)).save(any(Member.class));  // save 메서드가 한번 호출 되었는지 확인
        assertEquals(member.getId(), userId);  // 반환된 id가 user의 id와 같은지 확인
    }

    @Test
    @DisplayName("유저 정보 조회 테스트")
    public void getUserInfoTest() {
        // given
        Member member = Member.builder()
                .id(1L)
                .username("seoji")
                .nickname("__seoji")
                .password("password")
                .email("seoji@naver.com")
                .password("password")
                .build();
        when(memberRepository.findById(member.getId())).thenReturn(Optional.of(member));

        // when
        GetMemberInfoResponse getMemberInfoResponse = memberService.getUserInfoById(member.getId());

        // then
        assertEquals(member.getUsername(), getMemberInfoResponse.getUsername());
        assertEquals(member.getNickname(), getMemberInfoResponse.getNickname());
        assertEquals(member.getEmail(), getMemberInfoResponse.getEmail());
    }
}
