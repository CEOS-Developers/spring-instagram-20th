package com.ceos20.instagram.dm.domain;

import com.ceos20.instagram.user.domain.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DMRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private Long id;

    // 대화에 참여하는 유저1
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user1_id")
    @NotNull
    private User userId1;

    // 대화에 참여하는 유저2
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user2_id")
    @NotNull
    private User userId2;

    // 읽지 않은 DM 수
    @Column(nullable = false)
    @Builder.Default
    private Long restChatCount= 0L;

    public void increaseRestChatCount() {
        restChatCount++;
    }
    public void decreaseRestChatCount() {
        restChatCount--;
    }
}
