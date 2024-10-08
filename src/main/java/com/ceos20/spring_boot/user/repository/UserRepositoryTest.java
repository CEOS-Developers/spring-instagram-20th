package com.ceos20.spring_boot.user.repository;

import com.ceos20.spring_boot.user.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest  // JPA 관련 컴포넌트만 로드하여 빠른 테스트 수행
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)  // 실제 DB 사용시 설정
public class UserRepositoryTest {



    private UserRepository userRepository;

    @Test
    public void testSaveUser() {
        // given: 테스트를 위한 User 엔티티 생성
        User user = User.builder()
                .name("John")
                .password("password123")
                .gender("Male")
                .birthday(LocalDateTime.now().minusYears(25))
                .profileImage("profile.jpg")
                .uploadPost(0L)
                .followerNum(0L)
                .followingNum(0L)
                .build();

        // when: User 엔티티 저장
        User savedUser = userRepository.save(user);

        // then: 저장된 값이 기대하는 값과 일치하는지 확인
        assertNotNull(savedUser.getUserId());
        assertEquals("John", savedUser.getName());
        assertEquals("Male", savedUser.getGender());
    }

    @Test
    public void testFindById() {
        // given: User 저장 후 ID로 조회 테스트
        User user = User.builder()
                .name("Alice")
                .password("alicepassword")
                .gender("Female")
                .birthday(LocalDateTime.now().minusYears(30))
                .profileImage("alice.jpg")
                .uploadPost(0L)
                .followerNum(0L)
                .followingNum(0L)
                .build();

        User savedUser = userRepository.save(user);

        // when: 저장된 ID로 조회
        Optional<User> foundUser = userRepository.findById(savedUser.getUserId());

        // then: 조회된 값이 일치하는지 확인
        assertTrue(foundUser.isPresent());
        assertEquals("Alice", foundUser.get().getName());
    }

    @Test
    public void testExistsByName() {
        // given: 이름이 "Bob"인 사용자 저장
        User user = User.builder()
                .name("Bob")
                .password("bobpassword")
                .gender("Male")
                .birthday(LocalDateTime.now().minusYears(22))
                .profileImage("bob.jpg")
                .uploadPost(0L)
                .followerNum(0L)
                .followingNum(0L)
                .build();

        userRepository.save(user);

        // when: "Bob"이라는 이름을 가진 사용자가 존재하는지 확인
        boolean exists = userRepository.existsByName("Bob");

        // then: 존재 여부 확인
        assertTrue(exists);
    }

    @Test
    public void testDeleteUser() {
        // given: User 저장 후 삭제
        User user = User.builder()
                .name("Charlie")
                .password("charliepassword")
                .gender("Male")
                .birthday(LocalDateTime.now().minusYears(28))
                .profileImage("charlie.jpg")
                .uploadPost(0L)
                .followerNum(0L)
                .followingNum(0L)
                .build();

        User savedUser = userRepository.save(user);

        // when: 사용자 삭제
        userRepository.deleteById(savedUser.getUserId());

        // then: 삭제 후 사용자 존재 여부 확인
        Optional<User> deletedUser = userRepository.findById(savedUser.getUserId());
        assertFalse(deletedUser.isPresent());
    }
}
