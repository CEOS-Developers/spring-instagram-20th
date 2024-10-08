package com.ceos20.spring_boot.user.service;

import com.ceos20.spring_boot.user.DTO.UserCreateDTO;
import com.ceos20.spring_boot.user.DTO.UserDTO;
import com.ceos20.spring_boot.user.DTO.UserProfileDTO;
import com.ceos20.spring_boot.user.domain.User;
import com.ceos20.spring_boot.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.Period;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 회원가입
    @Transactional
    public UserDTO registerUser(UserCreateDTO userCreateDTO) {
        // 1. 만 14세 이상만 가입 가능
        if (!isValidAge(userCreateDTO.getBirthday())){
            throw new IllegalArgumentException("만 14세 이상만 가입이 가능합니다.");
        }

        // 2. 중복된 이름 허용 안함
        if (userRepository.existsByName(userCreateDTO.getName())) {
            throw new IllegalArgumentException("이미 해당 이름으로 가입된 사용자가 존재합니다.");
        }

        // DTO -> 엔티티 변환
        User user = User.builder()
                .name(userCreateDTO.getName())
                .password(userCreateDTO.getPassword())
                .gender(userCreateDTO.getGender())
                .birthday(userCreateDTO.getBirthday())
                .profileImage(userCreateDTO.getProfileImage())
                .uploadPost(0L) //기본값 설정
                .followerNum(0L)
                .followingNum(0L)
                .build();

        // 저장 후 UserDTO 반환
        User savedUser = userRepository.save(user);
        return new UserDTO(
            savedUser.getUserId(),        // 자동 생성된 userId
            savedUser.getName(),
            savedUser.getPassword(),
            savedUser.getGender(),
            savedUser.getBirthday(),
            savedUser.getProfileImage(),
            savedUser.getUploadPost(),
            savedUser.getFollowerNum(),
            savedUser.getFollowingNum()
        );
    }

    // 만 14세 이상인지 확인
    // 생년월일을 받아 나이가 14세 이상인지 확인하는 메서드
    private boolean isValidAge(LocalDateTime birthday) {
        LocalDate birthDate = birthday.toLocalDate();
        LocalDate currentDate = LocalDate.now();
        int age = Period.between(birthDate, currentDate).getYears();
        return age >= 14;
    }

    // 사용자 조회
    @Transactional(readOnly = true)
    public UserProfileDTO getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(()->new RuntimeException("User Not Found"));

        // User 엔티티를 UserDTO로 변환하여 반환
        return new UserProfileDTO(
                user.getUserId(),
                user.getName(),
                user.getProfileImage(),
                user.getUploadPost(),
                user.getFollowerNum(),
                user.getFollowingNum()
        );
    }

    // 사용자 삭제
    @Transactional
    public void deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new RuntimeException("User not found with id: " + userId);
        }
        userRepository.deleteById(userId);
    }
}
