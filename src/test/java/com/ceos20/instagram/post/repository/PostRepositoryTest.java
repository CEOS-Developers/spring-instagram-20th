package com.ceos20.instagram.post.repository;

import com.ceos20.instagram.post.domain.Post;
import com.ceos20.instagram.user.domain.User;
import com.ceos20.instagram.user.repository.UserRepository;
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
public class PostRepositoryTest {

    private final PostRepository postRepository;
    private final EntityManager em;

    @Autowired
    public PostRepositoryTest(PostRepository postRepository, EntityManager em) {
        this.postRepository = postRepository;
        this.em = em;
    }

    @Test
    @DisplayName("author로 게시글 조회 테스트")
    void findByAuthorTest() throws Exception {
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

        User user3 = User.builder()
                .username("user3")
                .nickname("user3")
                .password("password")
                .build();
        em.persist(user3);

        Post post1 = Post.builder()
                .content("user1이 작성한 post1")
                .author(user1)
                .build();
        em.persist(post1);

        Post post2 = Post.builder()
                .content("user1이 작성한 post2")
                .author(user1)
                .build();
        em.persist(post2);

        Post post3 = Post.builder()
                .content("user2이 작성한 post3")
                .author(user2)
                .build();
        em.persist(post3);

        // when
        List<Post> findPosts = postRepository.findByAuthor(user1);

        // then
        assertEquals(2, findPosts.size());

        for (Post findPost : findPosts) {
            System.out.println("content: " + findPost.getContent());
        }
    }

    @Test
    @DisplayName("N+1 문제")
    void findPostLazy() {
        // given
        User user1 = User.builder()
                .username("user1")
                .nickname("user1")
                .password("password")
                .email("user1@naver.com")
                .build();
        em.persist(user1);

        User user2 = User.builder()
                .username("user2")
                .nickname("user2")
                .password("password")
                .email("user2@naver.com")
                .build();
        em.persist(user2);

        Post post1 = Post.builder()
                .author(user1)
                .content("post1")
                .build();
        em.persist(post1);

        Post post2 = Post.builder()
                .author(user2)
                .content("post2")
                .build();
        em.persist(post2);

        em.flush();
        em.clear();

        // when
        System.out.println("=======================query start===========================");
        List<Post> posts = postRepository.findAllPost();

        // then
        for (Post post : posts) {
            System.out.println("post = " + post.getContent());
            System.out.println("post.getAuthor().getClass() = " + post.getAuthor().getClass());
            System.out.println("post.getAuthor().getUsername() = " + post.getAuthor().getUsername());
        }
    }
}
