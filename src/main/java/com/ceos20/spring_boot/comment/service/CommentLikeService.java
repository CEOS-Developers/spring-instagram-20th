package com.ceos20.spring_boot.comment.service;

import com.ceos20.spring_boot.comment.domain.Comment;
import com.ceos20.spring_boot.comment.domain.CommentLike;
import com.ceos20.spring_boot.comment.dto.CommentLikeRequestDto;
import com.ceos20.spring_boot.comment.dto.CommentLikeResponseDto;
import com.ceos20.spring_boot.comment.repository.CommentLikeRepository;
import com.ceos20.spring_boot.comment.repository.CommentRepository;
import com.ceos20.spring_boot.user.domain.User;
import com.ceos20.spring_boot.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentLikeService {

    private final CommentLikeRepository commentLikeRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    @Autowired
    public CommentLikeService(CommentLikeRepository commentLikeRepository, UserRepository userRepository, CommentRepository commentRepository) {
        this.commentLikeRepository = commentLikeRepository;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
    }

    // 댓글 좋아요 추가
    @Transactional
    public CommentLikeResponseDto addCommentLike(CommentLikeRequestDto commentLikeRequestDto) {
        User user = userRepository.findById(commentLikeRequestDto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + commentLikeRequestDto.getUserId()));
        Comment comment = commentRepository.findById(commentLikeRequestDto.getCommentId())
                .orElseThrow(() -> new RuntimeException("Comment not found with id: " + commentLikeRequestDto.getCommentId()));

        CommentLike commentLike = CommentLikeRequestDto.toEntity(commentLikeRequestDto, user, comment);
        CommentLike savedCommentLike = commentLikeRepository.save(commentLike);
        return CommentLikeResponseDto.fromEntity(savedCommentLike);
    }

    // 댓글 좋아요 삭제
    @Transactional
    public void deleteCommentLike(Long commentLikeId) {
        if (!commentLikeRepository.existsById(commentLikeId)) {
            throw new RuntimeException("Comment like not found with id: " + commentLikeId);
        }
        commentLikeRepository.deleteById(commentLikeId);
    }

    // 댓글 좋아요 조회
    @Transactional(readOnly = true)
    public CommentLikeResponseDto getCommentLikeById(Long commentLikeId) {
        CommentLike commentLike = commentLikeRepository.findById(commentLikeId)
                .orElseThrow(() -> new RuntimeException("Comment like not found with id: " + commentLikeId));
        return CommentLikeResponseDto.fromEntity(commentLike);
    }
}
