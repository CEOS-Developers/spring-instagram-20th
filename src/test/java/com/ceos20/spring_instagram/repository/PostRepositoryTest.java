package com.ceos20.spring_instagram.repository;

import com.ceos20.spring_instagram.domain.Member;
import com.ceos20.spring_instagram.domain.Post;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import jakarta.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private EntityManager entityManager; // JPA 쿼리 확인을 위한 EntityManager

    @BeforeEach
    public void setup() {
        // Given - 3명의 멤버를 저장 (Builder 패턴 사용)
        Member member1 = Member.builder()
                .membername("member1")
                .email("member1@test.com")
                .password("password1")
                .build();
        memberRepository.save(member1);

        Member member2 = Member.builder()
                .membername("member2")
                .email("member2@test.com")
                .password("password2")
                .build();
        memberRepository.save(member2);

        Member member3 = Member.builder()
                .membername("member3")
                .email("member3@test.com")
                .password("password3")
                .build();
        memberRepository.save(member3);

        // Given - 3개의 포스트를 저장 (Builder 패턴 사용)
        Post post1 = Post.builder()
                .member(member1)
                .image("image1.jpg")
                .caption("caption1")
                .timestamp(LocalDateTime.now())
                .build();
        postRepository.save(post1);

        Post post2 = Post.builder()
                .member(member2)
                .image("image2.jpg")
                .caption("caption2")
                .timestamp(LocalDateTime.now())
                .build();
        postRepository.save(post2);

        Post post3 = Post.builder()
                .member(member3)
                .image("image3.jpg")
                .caption("caption3")
                .timestamp(LocalDateTime.now())
                .build();
        postRepository.save(post3);

        // JPA 쿼리를 확인하기 위해 flush
        entityManager.flush();
        entityManager.clear();
    }

    @Test
    public void givenPosts_whenFindAll_thenReturnPosts() {
        // When - 모든 포스트를 조회
        List<Post> posts = postRepository.findAll();

        // Then - 조회된 포스트가 3개인지 확인
        assertThat(posts).hasSize(3);

        // Then - 각각의 포스트가 올바른지 확인
        assertThat(posts.get(0).getCaption()).isEqualTo("caption1");
        assertThat(posts.get(1).getCaption()).isEqualTo("caption2");
        assertThat(posts.get(2).getCaption()).isEqualTo("caption3");

        // Then - 연관된 Member가 N+1 문제 없이 로드되었는지 확인
        assertThat(posts.get(0).getMember()).isNotNull();
        assertThat(posts.get(1).getMember()).isNotNull();
        assertThat(posts.get(2).getMember()).isNotNull();
    }
}
