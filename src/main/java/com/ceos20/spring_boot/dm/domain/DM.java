package com.ceos20.spring_boot.dm.domain;

import com.ceos20.spring_boot.user.domain.User;
import jakarta.persistence.*;
import lombok.*;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class DM {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dm_id")
    private Long dmId;

    @Column(name = "dm_content", columnDefinition = "TEXT")
    private String dmContent;

    @Column(name = "sent_at", nullable = false)
    private Timestamp sentAt;

    @Column(name = "is_read", nullable = false)
    private Boolean isRead;

    @Column(name = "emotion", length = 50)
    private String emotion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ref_dm_id")
    private DM refDMId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", nullable = false)
    private User senderId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id", nullable = false)
    private User receiverId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dm_room_id", nullable = false)
    private DMRoom dmRoomId;
}
