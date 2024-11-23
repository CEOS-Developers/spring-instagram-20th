package com.ceos20.spring_boot.user.service;

import com.ceos20.spring_boot.security.TokenProvider;
import com.ceos20.spring_boot.user.DTO.UserCreateDTO;
import com.ceos20.spring_boot.user.DTO.UserDTO;
import com.ceos20.spring_boot.user.DTO.UserProfileDTO;
import com.ceos20.spring_boot.user.domain.User;
import com.ceos20.spring_boot.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, TokenProvider tokenProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
    }

    // 회원가입
    @Transactional
    public UserDTO registerUser(UserCreateDTO userCreateDTO) {
        if (!isValidAge(userCreateDTO.getBirthday())) {
            throw new IllegalArgumentException("만 14세 이상만 가입이 가능합니다.");
        }

        if (userRepository.existsByName(userCreateDTO.getName())) {
            throw new IllegalArgumentException("이미 해당 이름으로 가입된 사용자가 존재합니다.");
        }

        User user = User.builder()
                .name(userCreateDTO.getName())
                .password(passwordEncoder.encode(userCreateDTO.getPassword())) // 비밀번호 암호화
                .gender(userCreateDTO.getGender())
                .birthday(userCreateDTO.getBirthday())
                .profileImage(userCreateDTO.getProfileImage())
                .uploadPost(0L)
                .followerNum(0L)
                .followingNum(0L)
                .build();

        User savedUser = userRepository.save(user);
        return new UserDTO(
                savedUser.getUserId(),
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

    // 로그인
    public String authenticateUser(String username, String password) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (passwordEncoder.matches(password, user.getPassword())) {
                return tokenProvider.createToken(username);
            }
        }
        throw new RuntimeException("Invalid username or password");
    }

    private boolean isValidAge(LocalDateTime birthday) {
        LocalDate birthDate = birthday.toLocalDate();
        LocalDate currentDate = LocalDate.now();
        int age = Period.between(birthDate, currentDate).getYears();
        return age >= 14;
    }
}
