package com.ceos20.spring_boot.post.service;

import com.ceos20.spring_boot.post.domain.PostLike;
import com.ceos20.spring_boot.post.repository.PostLikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostLikeService {

    private final PostLikeRepository postLikeRepository;

    @Autowired
    public PostLikeService(PostLikeRepository postLikeRepository) {
        this.postLikeRepository = postLikeRepository;
    }

    // 게시물 좋아요 추가
    @Transactional
    public PostLike addPostLike(PostLike postLike) {
        return postLikeRepository.save(postLike);
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
