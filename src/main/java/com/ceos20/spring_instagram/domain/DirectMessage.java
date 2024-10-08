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
public class DirectMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long messageId;

    @ManyToOne
    @JoinColumn(name = "senderId")
    private Member sender;

    @ManyToOne
    @JoinColumn(name = "receiverId")
    private Member receiver;

    private String text;

    @CreatedDate // 생성 시간 자동 설정
    private LocalDateTime timestamp;
}
