package com.ceos20.instagram.auth.service;

import com.ceos20.instagram.global.exception.BadRequestException;
import com.ceos20.instagram.global.exception.ExceptionCode;
import com.ceos20.instagram.global.exception.NotFoundException;
import com.ceos20.instagram.auth.domain.CustomUserDetails;
import com.ceos20.instagram.jwt.JwtUtil;
import com.ceos20.instagram.user.domain.User;
import com.ceos20.instagram.auth.dto.JoinRequestDto;
import com.ceos20.instagram.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;
@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtUtil jwtUtil;

    private final RedisService redisService;

    @Transactional
    public User create(JoinRequestDto joinRequestDto){
        String nickname = joinRequestDto.getNickname();
        Boolean isExist = userRepository.existsByNickname(nickname);
        if(isExist) throw new BadRequestException(ExceptionCode.ALREADY_EXIST_NICKNAME);

        String encPassword = bCryptPasswordEncoder.encode(joinRequestDto.getPassword());
        User user = joinRequestDto.toEntity(joinRequestDto, encPassword);

        return userRepository.save(user);

    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User findUser = userRepository.findByNickname(username)
                .orElseThrow(() -> new BadRequestException(ExceptionCode.NOT_FOUND_USER));

        if(findUser == null){
            throw new UsernameNotFoundException("해당 유저를 찾을 수 없습니다.");
        }
        return new CustomUserDetails(findUser);
    }

    public String reissue(String refreshToken) {
        String nickname = jwtUtil.getUserNickname(refreshToken);

        Optional<String> optionalToken = redisService.find(nickname);
        if (optionalToken.isPresent()) {
            if (!optionalToken.get().equals(refreshToken)) {
                throw new RuntimeException("유효하지 않은 refreshToken 입니다.");
            }

            User user = userRepository.findByNickname(nickname)
                    .orElseThrow(() -> new NotFoundException(ExceptionCode.NOT_FOUND_USER));

            return jwtUtil.generateAccessToken(nickname, user.getRole().toString());
        } else {
            throw new RuntimeException("존재하지 않는 토큰입니다.");
        }
    }
}
