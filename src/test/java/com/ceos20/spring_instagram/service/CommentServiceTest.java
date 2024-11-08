package com.ceos20.spring_instagram.service;

import com.ceos20.spring_instagram.domain.Comment;
import com.ceos20.spring_instagram.domain.Post;
import com.ceos20.spring_instagram.domain.Member;
import com.ceos20.spring_instagram.repository.CommentRepository;
import com.ceos20.spring_instagram.repository.PostRepository;
import com.ceos20.spring_instagram.repository.MemberRepository;
import com.ceos20.spring_instagram.dto.CommentDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.List;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class CommentServiceTest {

    @InjectMocks
    private CommentService commentService;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private PostRepository postRepository;

    @Mock
    private MemberRepository memberRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // 댓글 추가 기능을 테스트하는 메서드
    @Test
    public void whenValidInput_thenAddComment() {
        Long postId = 1L;
        Long memberId = 1L;
        String text = "Test comment";

        Post post = Post.builder().postId(postId).build();
        Member member = Member.builder().memberId(memberId).build();

        when(postRepository.findById(postId)).thenReturn(Optional.of(post));
        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));

        Comment comment = Comment.builder().post(post).member(member).text(text).build();
        when(commentRepository.save(any(Comment.class))).thenReturn(comment);

        // When: CommentService의 addComment 호출
        CommentDTO result = commentService.addComment(postId, memberId, text);

        // Then: 반환된 DTO의 Post, Member, Text가 기대값과 일치하는지 확인
        assertThat(result.getPostId()).isEqualTo(postId);
        assertThat(result.getMemberId()).isEqualTo(memberId);
        assertThat(result.getText()).isEqualTo(text);

        verify(postRepository, times(1)).findById(postId);
        verify(memberRepository, times(1)).findById(memberId);
        verify(commentRepository, times(1)).save(any(Comment.class));
    }

    // 대댓글 추가 기능을 테스트하는 메서드
    @Test
    public void whenValidInput_thenAddReply() {
        Long postId = 1L;
        Long memberId = 1L;
        Long parentCommentId = 1L;
        String text = "Test reply";

        Post post = Post.builder().postId(postId).build();
        Member member = Member.builder().memberId(memberId).build();
        Comment parentComment = Comment.builder().commentId(parentCommentId).build();

        when(postRepository.findById(postId)).thenReturn(Optional.of(post));
        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));
        when(commentRepository.findById(parentCommentId)).thenReturn(Optional.of(parentComment));

        Comment reply = Comment.builder().post(post).member(member).parentComment(parentComment).text(text).build();
        when(commentRepository.save(any(Comment.class))).thenReturn(reply);

        // When: CommentService의 addReply 호출
        Comment result = commentService.addReply(postId, memberId, parentCommentId, text);

        // Then: 반환된 대댓글의 Post, Member, 부모 Comment, Text가 기대값과 일치하는지 확인
        assertThat(result.getPost()).isEqualTo(post);
        assertThat(result.getMember()).isEqualTo(member);
        assertThat(result.getParentComment()).isEqualTo(parentComment);
        assertThat(result.getText()).isEqualTo(text);

        verify(postRepository, times(1)).findById(postId);
        verify(memberRepository, times(1)).findById(memberId);
        verify(commentRepository, times(1)).findById(parentCommentId);
        verify(commentRepository, times(1)).save(any(Comment.class));
    }

    // 특정 댓글의 대댓글 리스트 조회 기능을 테스트하는 메서드
    @Test
    public void whenValidParentCommentId_thenGetReplies() {
        Long parentCommentId = 1L;
        Comment parentComment = Comment.builder().commentId(parentCommentId).build();

        Comment reply1 = Comment.builder().commentId(2L).parentComment(parentComment).build();
        Comment reply2 = Comment.builder().commentId(3L).parentComment(parentComment).build();

        when(commentRepository.findById(parentCommentId)).thenReturn(Optional.of(parentComment));
        when(commentRepository.findByParentComment(parentComment)).thenReturn(Arrays.asList(reply1, reply2));

        List<Comment> replies = commentService.getReplies(parentCommentId);

        assertThat(replies).hasSize(2);
        assertThat(replies.get(0).getParentComment()).isEqualTo(parentComment);
        assertThat(replies.get(1).getParentComment()).isEqualTo(parentComment);

        verify(commentRepository, times(1)).findById(parentCommentId);
        verify(commentRepository, times(1)).findByParentComment(parentComment);
    }
}
