package com.ceos20.spring_boot.dm.domain;

import com.ceos20.spring_boot.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class DMRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dm_room_id")
    private Long dmRoomId;

    @Column(name = "not_read_dm_num", nullable = false)
    private int notReadDMNum;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id_1", nullable = false)
    private User userId_1;  // DM 방의 첫 번째 사용자

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id_2", nullable = false)
    private User userId_2;  // DM 방의 두 번째 사용자
}
