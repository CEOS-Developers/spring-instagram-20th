package com.ceos20.instagram.user.service;

import com.ceos20.instagram.user.domain.User;
import com.ceos20.instagram.user.dto.GetUserInfoResponse;
import com.ceos20.instagram.user.dto.SaveUserRequest;
import com.ceos20.instagram.user.repository.UserRepository;
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
public class UserServiceTest {

    @Mock  // userRepository를 mock으로 주입하므로 모의 동작을 설정해야 한다.
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User testUser;

    @Test
    @DisplayName("유저 저장 테스트")
    public void saveUserTest() {
        // given
        SaveUserRequest saveUserRequest = SaveUserRequest.builder()
                .username("seoji")
                .nickname("__seoji")
                .email("seoji@naver.com")
                .password("password")
                .build();
        User user = saveUserRequest.toEntity();

        when(userRepository.save(any(User.class))).thenReturn(user);

        // when
        Long userId = userService.saveUser(saveUserRequest);

        // then
        verify(userRepository, times(1)).save(any(User.class));  // save 메서드가 한번 호출 되었는지 확인
        assertEquals(user.getId(), userId);  // 반환된 id가 user의 id와 같은지 확인
    }

    @Test
    @DisplayName("유저 정보 조회 테스트")
    public void getUserInfoTest() {
        // given
        User user = User.builder()
                .id(1L)
                .username("seoji")
                .nickname("__seoji")
                .password("password")
                .email("seoji@naver.com")
                .password("password")
                .build();
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        // when
        GetUserInfoResponse getUserInfoResponse = userService.getUserInfoById(user.getId());

        // then
        assertEquals(user.getUsername(), getUserInfoResponse.getUsername());
        assertEquals(user.getNickname(), getUserInfoResponse.getNickname());
        assertEquals(user.getEmail(), getUserInfoResponse.getEmail());
    }
}
