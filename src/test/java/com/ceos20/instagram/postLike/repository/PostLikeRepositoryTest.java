package com.ceos20.instagram.postLike.repository;

import com.ceos20.instagram.post.domain.Post;
import com.ceos20.instagram.postLike.domain.PostLike;
import com.ceos20.instagram.user.domain.User;
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
        User user1 = User.builder()
                .username("user1")
                .nickname("user1")
                .password("password")
                .build();
        em.persist(user1);

        User user2 = User.builder()
                .username("user2")
                .nickname("user2")
                .password("password")
                .build();
        em.persist(user2);

        Post post = Post.builder()
                .content("post")
                .author(user1)
                .build();
        em.persist(post);

        PostLike postLike = PostLike.builder()
                .post(post)
                .user(user2)
                .build();
        em.persist(postLike);

        // when
        List<PostLike> findPostLikes = postLikeRepository.findByPost(post);

        // then
        for(PostLike findPostLike : findPostLikes) {
            System.out.println("좋아요 누른 사람: " + findPostLike.getUser().getUsername());
            assertEquals(postLike.getId(), findPostLike.getId());
        }
    }
}
