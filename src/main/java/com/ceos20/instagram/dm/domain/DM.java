package com.ceos20.instagram.dm.domain;

import com.ceos20.instagram.user.domain.User;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class DM {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dm_int")
    private Long id;

    // 내용
    @Column(nullable = false)
    private String content;

    // 전송일
    @Column(nullable = false)
    private LocalDateTime sendedAt = LocalDateTime.now();

    // 읽음 여부
    @Column(nullable = false)
    private boolean isRead = false;

    // 공감
    @Column(length = 1)
    private String emotion;

    // 보낸 사용자
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", nullable = false)
    private User senderID;

    // 받는 사용자
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User receiverID;

    // DM방
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", nullable = false)
    private DMRoom dmRoomID;
}
