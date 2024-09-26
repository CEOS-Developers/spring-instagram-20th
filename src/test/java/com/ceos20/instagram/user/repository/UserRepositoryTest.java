package com.ceos20.instagram.user.repository;

import com.ceos20.instagram.user.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
public class UserRepositoryTest {

    private final UserRepository userRepository;

    @Autowired
    public UserRepositoryTest(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Test
    @DisplayName("id로 유저 조회 테스트")
    void findByIdTest() throws Exception {
        // given
        User user = User.builder()
                .username("최서지")
                .nickname("seoji")
                .password("password")
                .build();

        // when
        userRepository.save(user);
        User findUser = userRepository.findById(user.getId());

        // then
        assertEquals(user.getId(), findUser.getId());
    }
}
