package com.ceos20.instagram.post.service;

import com.ceos20.instagram.image.domain.Image;
import com.ceos20.instagram.image.repository.ImageRepository;
import com.ceos20.instagram.post.domain.Post;
import com.ceos20.instagram.post.dto.CreatePostRequest;
import com.ceos20.instagram.post.dto.GetPostResponse;
import com.ceos20.instagram.post.repository.PostRepository;
import com.ceos20.instagram.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;
    private final ImageRepository imageRepository;

    // 게시글 생성
    @Transactional
    public void createPost(CreatePostRequest createPostRequest, User user) {

        // 글 저장
        Post post = Post.toEntity(createPostRequest, user);
        postRepository.save(post);

        // 이미지 저장
        List<Image> images = createPostRequest.getImages().stream()
                .map(image -> Image.toEntity(post, image))
                .collect(Collectors.toList());
        imageRepository.saveAll(images);
    }

    // 게시글 본문 수정
    @Transactional
    public void updatePostContent(String content, Long postId) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 postId 입니다."));
        post.updateContent(content);
        postRepository.save(post);
    }

    // 게시글 삭제
    @Transactional
    public void deletePost(Long postId) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 postId 입니다."));
        postRepository.delete(post);
    }

    // id로 게시글 조회
    public GetPostResponse getPostById(Long postId) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 postId 입니다."));

        return GetPostResponse.fromEntity(post);
    }

    // 특정 user가 작성한 게시글 리스트 반환
    public List<GetPostResponse> getPostsByUser(User user) {

        List<Post> posts = postRepository.findByAuthor(user);

        return posts.stream()
                .map(post -> GetPostResponse.fromEntity(post))
                .collect(Collectors.toList());
    }
}
