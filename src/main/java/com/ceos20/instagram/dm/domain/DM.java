package com.ceos20.instagram.dm.domain;

import com.ceos20.instagram.user.domain.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DM {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dm_int")
    private Long id;

    // 내용
    @NotNull
    private String content;

    // 전송일
    @NotNull
    private LocalDateTime sendedAt = LocalDateTime.now();

    // 읽음 여부
    @Builder.Default
    @NotNull
    private boolean isRead = false;

    // 공감
    @Size(min=1, max=1)
    private String emotion;

    // 보낸 사용자
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="sender_id")
    @NotNull
    private User senderId;

    // 받는 사용자
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="receiver_id")
    @NotNull
    private User receiverId;

    // DM방
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    @NotNull
    private DMRoom dmRoomId;
}
