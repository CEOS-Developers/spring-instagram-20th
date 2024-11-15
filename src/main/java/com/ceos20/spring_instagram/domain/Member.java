package com.ceos20.spring_instagram.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Builder // 빌더 패턴 적용
@NoArgsConstructor // 기본 생성자
@AllArgsConstructor // 모든 필드 포함 생성자
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column(nullable = false)
    private String membername;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    private String profilePicture;

    // 권한을 반환하는 메서드
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 예를 들어, "ROLE_USER" 권한을 부여
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }
}

