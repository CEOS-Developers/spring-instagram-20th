package com.ceos20.instagram.post.service;

import com.ceos20.instagram.image.domain.Image;
import com.ceos20.instagram.image.repository.ImageRepository;
import com.ceos20.instagram.post.domain.Post;
import com.ceos20.instagram.post.dto.CreatePostRequest;
import com.ceos20.instagram.post.dto.GetPostResponse;
import com.ceos20.instagram.post.repository.PostRepository;
import com.ceos20.instagram.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class PostServiceTest {
    @Mock
    private PostRepository postRepository;
    @Mock
    private ImageRepository imageRepository;
    @InjectMocks   // 모킹된 postRepository와 imageRepository를 postService에 주입
    private PostService postService;

    private User user;
    private Post post;

    private List<Image> images = new ArrayList<>();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = User.builder()
                .id(1L)
                .username("seoji")
                .nickname("__seoji")
                .email("seoji@naver.com")
                .password("password")
                .build();

        post = Post.builder()
                .id(1L)
                .images(images)
                .author(user)
                .content("post 본문")
                .build();

        images.add(Image.builder()
                .index(1L)
                .post(post)
                .imageUrl("image1")
                .build());

        images.add(Image.builder()
                .index(2L)
                .post(post)
                .imageUrl("image2")
                .build());
    }

    @Test
    @DisplayName("게시글 생성 테스트")
    void createPostTest() {
        // given
        CreatePostRequest request = CreatePostRequest.builder()
                .content("게시글 내용")
                .images(List.of(new CreatePostRequest.Image("imageUrl", 1L)))
                .build();

        // when
        postService.createPost(request, user);

        // then
        verify(postRepository, times(1)).save(any(Post.class));
    }

    @Test
    @DisplayName("게시글 본문 수정 테스트")
    void updatePostContentTest() {
        // given
        when(postRepository.findById(post.getId())).thenReturn(Optional.of(post));

        // when
        postService.updatePostContent("수정된 게시글 본문", 1L);

        // then
        verify(postRepository, times(1)).findById(1L);
        verify(postRepository, times(1)).save(post);
        assertEquals("수정된 게시글 본문", post.getContent());
    }

    @Test
    @DisplayName("id로 게시글 조회")
    void getPostByIdTest() {
        // given
        when(postRepository.findById(post.getId())).thenReturn(Optional.of(post));

        // when
        GetPostResponse response = postService.getPostById(post.getId());

        // then
        assertEquals(post.getContent(), response.getContent());
        assertEquals(2, response.getImageList().size());
        System.out.println("content: " + response.getContent());
    }

    @Test
    @DisplayName("특정 user가 작성한 게시글 조회")
    void getPostByUserTest() {
        // given
        List<Post> posts = Arrays.asList(post);
        when(postRepository.findByAuthor(user)).thenReturn(posts);

        // when
        List<GetPostResponse> responses = postService.getPostsByUser(user);

        // then
        for(GetPostResponse response : responses) {
            assertEquals(post.getContent(), response.getContent());
            assertEquals(2, response.getImageList().size());
        }
        assertEquals(1, responses.size());
    }
}
