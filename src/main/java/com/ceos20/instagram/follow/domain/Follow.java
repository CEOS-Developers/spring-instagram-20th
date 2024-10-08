package com.ceos20.instagram.follow.domain;

import com.ceos20.instagram.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "follow_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_user_id")
    private User fromUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_user_id")
    private User toUser;

    public static Follow toEntity(User fromUser, User toUser) {
        return Follow.builder()
                .fromUser(fromUser)
                .toUser(toUser)
                .build();
    }
}
