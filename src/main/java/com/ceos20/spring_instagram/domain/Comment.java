package com.ceos20.spring_instagram.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Builder // 빌더 패턴 적용
@NoArgsConstructor // 기본 생성자
@AllArgsConstructor // 모든 필드 포함 생성자
@EntityListeners(AuditingEntityListener.class) // JPA Auditing 기능 활성화
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @ManyToOne
    @JoinColumn(name = "postId")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "memberId")
    private Member member;

    private String text;

    @CreatedDate
    private LocalDateTime timestamp;

    // 자기 참조 관계 설정 (대댓글)
    @ManyToOne
    @JoinColumn(name = "parent_comment_id")
    private Comment parentComment;

    // 대댓글 리스트
    @OneToMany(mappedBy = "parentComment", cascade = CascadeType.ALL)
    private List<Comment> replies;
}
