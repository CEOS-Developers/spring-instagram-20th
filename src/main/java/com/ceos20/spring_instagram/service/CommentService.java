package com.ceos20.spring_instagram.service;

import com.ceos20.spring_instagram.domain.Comment;
import com.ceos20.spring_instagram.domain.Post;
import com.ceos20.spring_instagram.domain.Member;
import com.ceos20.spring_instagram.global.exception.CommentNotFoundException;
import com.ceos20.spring_instagram.global.exception.MemberNotFoundException;
import com.ceos20.spring_instagram.global.exception.PostNotFoundException;
import com.ceos20.spring_instagram.repository.CommentRepository;
import com.ceos20.spring_instagram.repository.PostRepository;
import com.ceos20.spring_instagram.repository.MemberRepository;
import com.ceos20.spring_instagram.dto.CommentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository, PostRepository postRepository, MemberRepository memberRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.memberRepository = memberRepository;
    }

    // 댓글 추가
    public CommentDTO addComment(Long postId, Long memberId, String text) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("잘못된 포스트 ID입니다."));
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException("잘못된 회원 ID입니다."));

        Comment comment = Comment.builder()
                .post(post)
                .member(member)
                .text(text)
                .build();

        Comment savedComment = commentRepository.save(comment);
        return CommentDTO.from(savedComment);
    }

    // 모든 댓글 가져오기 (특정 포스트에 대한 댓글)
    public List<CommentDTO> getAllCommentsByPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("잘못된 포스트 ID입니다."));

        List<Comment> comments = commentRepository.findByPost(post);
        return comments.stream()
                .map(CommentDTO::from)
                .collect(Collectors.toList());
    }

    // 특정 댓글 조회
    public CommentDTO getCommentById(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException("해당 댓글이 존재하지 않습니다."));
        return CommentDTO.from(comment);
    }

    // 댓글 삭제
    public void deleteComment(Long commentId) {
        if (!commentRepository.existsById(commentId)) {
            throw new CommentNotFoundException("해당 댓글이 존재하지 않습니다.");
        }
        commentRepository.deleteById(commentId);
    }

    // 대댓글 추가
    public Comment addReply(Long postId, Long memberId, Long parentCommentId, String text) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("잘못된 포스트 ID입니다."));
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException("잘못된 회원 ID입니다."));
        Comment parentComment = commentRepository.findById(parentCommentId)
                .orElseThrow(() -> new CommentNotFoundException("해당 ID의 부모 댓글을 찾을 수 없습니다."));

        Comment reply = Comment.builder()
                .post(post)
                .member(member)
                .parentComment(parentComment) // 부모 댓글 설정
                .text(text)
                .build();

        return commentRepository.save(reply);
    }

    // 특정 댓글의 대댓글 조회
    public List<Comment> getReplies(Long parentCommentId) {
        Comment parentComment = commentRepository.findById(parentCommentId)
                .orElseThrow(() -> new CommentNotFoundException("해당 ID의 부모 댓글을 찾을 수 없습니다."));

        return commentRepository.findByParentComment(parentComment);
    }
}
