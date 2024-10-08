package com.ceos20.instagram.user.repository;

import com.ceos20.instagram.post.domain.Post;
import com.ceos20.instagram.post.repository.PostRepository;
import com.ceos20.instagram.user.repository.UserRepository;
import com.ceos20.instagram.user.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class PostRepositoryTest {
    @Autowired
    UserRepository userRepository;

    @Autowired
    PostRepository postRepository;

    @Test
    @DisplayName("user가 생성한 포스트 전부 조회")
    public void findPostsByUser() {
        // When
        User user1 = User.builder()
                .nickname("user1")
                .password("1234")
                .build();
        userRepository.save(user1);

        Post post1 = Post.builder()
                .writer(user1)
                .content("1234")
                .build();

        Post post2 = Post.builder()
                .writer(user1)
                .content("1234")
                .build();

        postRepository.save(post1);
        postRepository.save(post2);

        List<Post> posts = postRepository.findPostByWriter_Id(user1.getId());

        assertEquals(2, posts.size());
    }
}
