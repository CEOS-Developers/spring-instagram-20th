package com.ceos20.instagram.domain.chatRoom.domain;

import com.ceos20.instagram.domain.member.domain.Member;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member1_id")
    private Member member1;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member2_id")
    private Member member2;

    public static ChatRoom toEntity(Member member1, Member member2) {
        return ChatRoom.builder()
                .member1(member1)
                .member2(member2)
                .build();
    }
}
