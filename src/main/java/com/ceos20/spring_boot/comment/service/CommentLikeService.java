package com.ceos20.spring_boot.comment.service;

import com.ceos20.spring_boot.comment.domain.CommentLike;
import com.ceos20.spring_boot.comment.repository.CommentLikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentLikeService {

    private final CommentLikeRepository commentLikeRepository;

    @Autowired
    public CommentLikeService(CommentLikeRepository commentLikeRepository) {
        this.commentLikeRepository = commentLikeRepository;
    }

    // 댓글 좋아요 추가
    @Transactional
    public CommentLike addCommentLike(CommentLike commentLike) {
        return commentLikeRepository.save(commentLike);
    }

    // 댓글 좋아요 삭제
    @Transactional
    public void deleteCommentLike(Long commentLikeId) {
        commentLikeRepository.deleteById(commentLikeId);
    }

    // 댓글 좋아요 조회
    @Transactional(readOnly = true)
    public CommentLike getCommentLikeById(Long commentLikeId) {
        return commentLikeRepository.findById(commentLikeId)
                .orElseThrow(() -> new RuntimeException("Comment like not found with id: " + commentLikeId));
    }
}
