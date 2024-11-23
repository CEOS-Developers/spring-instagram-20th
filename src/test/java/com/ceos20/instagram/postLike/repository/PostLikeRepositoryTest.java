package com.ceos20.instagram.postLike.repository;

import com.ceos20.instagram.domain.post.domain.Post;
import com.ceos20.instagram.domain.postLike.domain.PostLike;
import com.ceos20.instagram.domain.member.domain.Member;
import com.ceos20.instagram.domain.postLike.repository.PostLikeRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
public class PostLikeRepositoryTest {

    private final PostLikeRepository postLikeRepository;
    private final EntityManager em;

    @Autowired
    public PostLikeRepositoryTest(PostLikeRepository postLikeRepository, EntityManager em) {
        this.postLikeRepository = postLikeRepository;
        this.em = em;
    }

    @Test
    @DisplayName("해당 게시글의 좋아요 조회 테스트")
    public void findByPostTest() throws Exception {
        // given
        Member member1 = Member.builder()
                .username("user1")
                .nickname("user1")
                .password("password")
                .build();
        em.persist(member1);

        Member member2 = Member.builder()
                .username("user2")
                .nickname("user2")
                .password("password")
                .build();
        em.persist(member2);

        Post post = Post.builder()
                .content("post")
                .author(member1)
                .build();
        em.persist(post);

        PostLike postLike = PostLike.builder()
                .post(post)
                .member(member2)
                .build();
        em.persist(postLike);

        // when
        List<PostLike> findPostLikes = postLikeRepository.findByPost(post);

        // then
        for(PostLike findPostLike : findPostLikes) {
            System.out.println("좋아요 누른 사람: " + findPostLike.getMember().getUsername());
            assertEquals(postLike.getId(), findPostLike.getId());
        }
    }
}
