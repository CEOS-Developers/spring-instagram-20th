package com.ceos20.spring_instagram.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.NoArgsConstructor;

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
}