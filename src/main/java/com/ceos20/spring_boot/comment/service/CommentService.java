package com.ceos20.spring_boot.comment.service;

import com.ceos20.spring_boot.comment.domain.Comment;
import com.ceos20.spring_boot.comment.dto.CommentRequestDto;
import com.ceos20.spring_boot.comment.dto.CommentResponseDto;
import com.ceos20.spring_boot.comment.repository.CommentRepository;
import com.ceos20.spring_boot.user.domain.User;
import com.ceos20.spring_boot.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
    }

    // 댓글 추가
    @Transactional
    public CommentResponseDto addComment(CommentRequestDto commentRequestDto) {
        User user = userRepository.findById(commentRequestDto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + commentRequestDto.getUserId()));

        Comment parentComment = commentRequestDto.getParentCommentId() != null
                ? commentRepository.findById(commentRequestDto.getParentCommentId()).orElse(null)
                : null;

        Comment comment = CommentRequestDto.toEntity(commentRequestDto, user, parentComment);
        Comment savedComment = commentRepository.save(comment);
        return CommentResponseDto.fromEntity(savedComment);
    }

    // 댓글 삭제
    @Transactional
    public void deleteComment(Long commentId) {
        if (!commentRepository.existsById(commentId)) {
            throw new RuntimeException("Comment not found with id: " + commentId);
        }
        commentRepository.deleteById(commentId);
    }

    // 댓글 조회
    @Transactional(readOnly = true)
    public CommentResponseDto getCommentById(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found with id: " + commentId));
        return CommentResponseDto.fromEntity(comment);
    }
}
