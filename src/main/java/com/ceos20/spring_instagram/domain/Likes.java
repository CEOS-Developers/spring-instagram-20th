package com.ceos20.spring_instagram.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder // 빌더 패턴 적용
@NoArgsConstructor // 기본 생성자
@AllArgsConstructor // 모든 필드 포함 생성자
@EntityListeners(AuditingEntityListener.class) // JPA Auditing 기능 활성화
public class Likes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long likeId;

    @ManyToOne
    @JoinColumn(name = "postId")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "memberId")
    private Member member;

    @CreatedDate
    private LocalDateTime timestamp;
}