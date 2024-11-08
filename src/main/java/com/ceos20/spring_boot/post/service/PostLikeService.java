package com.ceos20.spring_boot.post.service;

import com.ceos20.spring_boot.post.domain.Post;
import com.ceos20.spring_boot.post.domain.PostLike;
import com.ceos20.spring_boot.post.dto.PostLikeRequestDto;
import com.ceos20.spring_boot.post.dto.PostLikeResponseDto;
import com.ceos20.spring_boot.post.repository.PostLikeRepository;
import com.ceos20.spring_boot.post.repository.PostRepository;
import com.ceos20.spring_boot.user.domain.User;
import com.ceos20.spring_boot.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostLikeService {

    private final PostLikeRepository postLikeRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Autowired
    public PostLikeService(PostLikeRepository postLikeRepository, PostRepository postRepository, UserRepository userRepository) {
        this.postLikeRepository = postLikeRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    // 게시물 좋아요 추가
    @Transactional
    public PostLikeResponseDto addPostLike(PostLikeRequestDto postLikeRequestDto) {
        Post post = postRepository.findById(postLikeRequestDto.getPostId())
                .orElseThrow(() -> new RuntimeException("Post not found with id: " + postLikeRequestDto.getPostId()));

        User user = userRepository.findById(postLikeRequestDto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + postLikeRequestDto.getUserId()));

        PostLike postLike = PostLikeRequestDto.toEntity(postLikeRequestDto, post, user);
        PostLike savedPostLike = postLikeRepository.save(postLike);
        return PostLikeResponseDto.fromEntity(savedPostLike);
    }

    // 게시물 좋아요 삭제
    @Transactional
    public void deletePostLike(Long postLikeId) {
        postLikeRepository.deleteById(postLikeId);
    }

    // 특정 게시물에 대한 좋아요 수 조회
    @Transactional(readOnly = true)
    public Long countPostLikesByPostId(Long postId) {
        return postLikeRepository.countByPostId(postId);
    }
}
