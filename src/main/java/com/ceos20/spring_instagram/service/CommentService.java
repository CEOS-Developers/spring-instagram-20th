package com.ceos20.spring_instagram.service;

import com.ceos20.spring_instagram.domain.Comment;
import com.ceos20.spring_instagram.domain.Post;
import com.ceos20.spring_instagram.domain.Member;
import com.ceos20.spring_instagram.repository.CommentRepository;
import com.ceos20.spring_instagram.repository.PostRepository;
import com.ceos20.spring_instagram.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    public Comment addComment(Long postId, Long memberId, String text) {
        Optional<Post> post = postRepository.findById(postId);
        Optional<Member> member = memberRepository.findById(memberId);

        if (post.isEmpty() || member.isEmpty()) {
            throw new IllegalArgumentException("잘못된 포스트 또는 회원 ID입니다.");
        }

        Comment comment = Comment.builder()
                .post(post.get())
                .member(member.get())
                .text(text)
                .build();

        return commentRepository.save(comment);
    }

    // 대댓글 추가
    public Comment addReply(Long postId, Long memberId, Long parentCommentId, String text) {
        Optional<Post> post = postRepository.findById(postId);
        Optional<Member> member = memberRepository.findById(memberId);
        Optional<Comment> parentComment = commentRepository.findById(parentCommentId);

        if (post.isEmpty() || member.isEmpty() || parentComment.isEmpty()) {
            throw new IllegalArgumentException("잘못된 입력입니다.");
        }

        Comment reply = Comment.builder()
                .post(post.get())
                .member(member.get())
                .parentComment(parentComment.get()) // 부모 댓글 설정
                .text(text)
                .build();

        return commentRepository.save(reply);
    }

    // 특정 댓글의 대댓글 조회
    public List<Comment> getReplies(Long parentCommentId) {
        Optional<Comment> parentComment = commentRepository.findById(parentCommentId);

        if (parentComment.isEmpty()) {
            throw new IllegalArgumentException("해당 ID의 부모 댓글을 찾을 수 없습니다.");
        }

        return commentRepository.findByParentComment(parentComment.get());
    }
}
