package com.ceos20.instagram.post.service;

import com.ceos20.instagram.post.domain.Post;
import com.ceos20.instagram.post.domain.PostImage;
import com.ceos20.instagram.post.dto.PostRequestDto;
import com.ceos20.instagram.post.repository.PostImageRepository;
import com.ceos20.instagram.post.repository.PostRepository;
import com.ceos20.instagram.user.domain.User;
import com.ceos20.instagram.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class PostServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private PostRepository postRepository;

    @Mock
    private PostImageRepository postImageRepository;

    @Mock
    private PostImageService postImageService;

    @InjectMocks
    private PostService postService;

    private User user;
    private Post post;
    private PostRequestDto postRequestDto;
    private PostImage postImage;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .nickname("testUser")
                .build();

        post = Post.builder()
                .content("Test content")
                .writer(user)
                .build();

        MockMultipartFile image1 = new MockMultipartFile("image1", "image1.png", "image/png", "image content".getBytes());
        MockMultipartFile image2 = new MockMultipartFile("image2", "image2.png", "image/png", "image content".getBytes());

        postRequestDto = PostRequestDto.builder()
                .content("Test content")
                .images(List.of(image1, image2))  // MultipartFile 리스트로 추가
                .build();

        postImage = PostImage.builder()
                .path("image1.png")
                .post(post)
                .build();
    }

    @Test
    void create() {
        when(userRepository.findByNickname("testUser")).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> postService.create(postRequestDto, "testUser")
        );

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Post not found", exception.getReason());
    }

    @Test
    void getPost() {
        when(postRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> postService.getPost(1L)
        );

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Post not found", exception.getReason());
    }

//    @Test
//    void delete() {
//        postService.delete(1L, );
//
//        verify(postRepository, times(1)).deleteById(1L);
//    }
}