package com.ceos20.instagram.follow.service;

import com.ceos20.instagram.domain.follow.dto.GetFollowerResponse;
import com.ceos20.instagram.domain.follow.dto.GetFollowingResponse;
import com.ceos20.instagram.domain.follow.repository.FollowRepository;
import com.ceos20.instagram.domain.follow.service.FollowService;
import com.ceos20.instagram.domain.member.domain.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class FollowServiceTest {

    @Mock
    private FollowRepository followRepository;
    @InjectMocks
    private FollowService followService;

    private Member member;
    private Member follower1;
    private Member follower2;

    @BeforeEach
    void setUp() {
        member = Member.builder()
                .id(1L)
                .nickname("user")
                .password("password")
                .build();

        follower1 = Member.builder()
                .id(2L)
                .nickname("follower1")
                .password("password")
                .build();

        follower2 = Member.builder()
                .id(3L)
                .nickname("follower2")
                .password("password")
                .build();
    }

    @Test
    @DisplayName("나를 팔로우하는 사람들 반환 테스트")
    void getFollowerTest() {
        // given
        List<Member> followers = Arrays.asList(follower1, follower2);
        when(followRepository.findByToMember(member)).thenReturn(followers);

        // when
        List<GetFollowerResponse> responses = followService.getFollower(member);

        // then
        assertEquals(2, responses.size());
        assertEquals(follower1.getId(), responses.get(0).getUserId());
        assertEquals(follower2.getId(), responses.get(1).getUserId());
    }

    @Test
    @DisplayName("내가 팔로우하는 사람들 리스트 반환")
    void getFollowingTest() {
        // given
        List<Member> following = Arrays.asList(follower1, follower2);
        when(followRepository.findByFromMember(member)).thenReturn(following);

        // when
        List<GetFollowingResponse> responses = followService.getFollowing(member);

        // then
        assertEquals(2, responses.size());
        assertEquals(follower1.getId(), responses.get(0).getUserId());
        assertEquals(follower2.getId(), responses.get(1).getUserId());
    }
}
