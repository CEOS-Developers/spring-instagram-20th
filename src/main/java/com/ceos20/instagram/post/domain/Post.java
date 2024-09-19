package com.ceos20.instagram.post.domain;

import com.ceos20.instagram.comment.domain.Comment;
import com.ceos20.instagram.user.domain.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor // Builder는 파라미터 있는 생성자가 필요
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="post_id")
    private Long id;

    private String content;
    private int like_num;

    @CreationTimestamp
    @Column(updatable=false)
    private LocalDateTime created_at;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @OneToMany(mappedBy="post")
    private List<PostImage> images=new ArrayList<>();

    @OneToMany(mappedBy="post")
    private List<Comment> comments=new ArrayList<>();


}
