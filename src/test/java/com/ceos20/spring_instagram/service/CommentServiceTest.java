package com.ceos20.spring_instagram.service;

import com.ceos20.spring_instagram.domain.Comment;
import com.ceos20.spring_instagram.domain.Post;
import com.ceos20.spring_instagram.domain.Member;
import com.ceos20.spring_instagram.repository.CommentRepository;
import com.ceos20.spring_instagram.repository.PostRepository;
import com.ceos20.spring_instagram.repository.MemberRepository;
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

    // CommentService를 테스트할 때 필요한 의존성을 주입받습니다.
    @InjectMocks
    private CommentService commentService;

    // Mock 객체로 의존성 주입: 실제 DB 접근 없이 Repository 동작을 테스트
    @Mock
    private CommentRepository commentRepository;

    @Mock
    private PostRepository postRepository;

    @Mock
    private MemberRepository memberRepository;

    // 테스트 시작 전에 Mock 객체를 초기화합니다.
    @BeforeEach
    public void setUp() {
        // Mockito 초기화
        MockitoAnnotations.openMocks(this);
        System.out.println("테스트 초기화 완료"); // 추가: 테스트 초기화 완료 메시지 출력
    }

    // 댓글 추가 기능을 테스트하는 메서드
    @Test
    public void whenValidInput_thenAddComment() {
        // Given: 테스트를 위한 가짜 postId와 memberId, 댓글 내용
        Long postId = 1L; // 1L은 long 타입 리터럴로, Post의 ID로 사용
        Long memberId = 1L; // Member의 ID로 사용
        String text = "Test comment"; // 댓글 내용

        System.out.println("댓글 추가 테스트 시작: postId=" + postId + ", memberId=" + memberId); // 추가

        // Post 객체 생성 및 Mock 설정
        Post post = Post.builder().postId(postId).build();
        // Member 객체 생성 및 Mock 설정
        Member member = Member.builder().memberId(memberId).build();

        // postId로 Post 조회 시, 가짜 post 객체 반환
        when(postRepository.findById(postId)).thenReturn(Optional.of(post));
        // memberId로 Member 조회 시, 가짜 member 객체 반환
        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));

        // commentRepository의 save() 호출 시, 댓글 저장 후 반환
        Comment comment = Comment.builder().post(post).member(member).text(text).build();
        when(commentRepository.save(any(Comment.class))).thenReturn(comment);

        // When: CommentService의 addComment 호출
        Comment result = commentService.addComment(postId, memberId, text);

        System.out.println("댓글 추가 결과: " + result); // 추가

        // Then: 반환된 댓글의 Post, Member, Text가 기대값과 일치하는지 확인
        assertThat(result.getPost()).isEqualTo(post);
        assertThat(result.getMember()).isEqualTo(member);
        assertThat(result.getText()).isEqualTo(text);

        // postRepository와 memberRepository의 findById 메서드가 각각 1번 호출되었는지 검증
        verify(postRepository, times(1)).findById(postId);
        verify(memberRepository, times(1)).findById(memberId);
        // commentRepository의 save 메서드가 1번 호출되었는지 검증
        verify(commentRepository, times(1)).save(any(Comment.class));
    }

    // 대댓글 추가 기능을 테스트하는 메서드
    @Test
    public void whenValidInput_thenAddReply() {
        // Given: 테스트를 위한 가짜 postId, memberId, parentCommentId, 대댓글 내용
        Long postId = 1L; // Post의 ID
        Long memberId = 1L; // Member의 ID
        Long parentCommentId = 1L; // 부모 댓글의 ID
        String text = "Test reply"; // 대댓글 내용

        System.out.println("대댓글 추가 테스트 시작: postId=" + postId + ", memberId=" + memberId + ", parentCommentId=" + parentCommentId); // 추가

        // Post, Member, 부모 Comment 객체 생성 및 Mock 설정
        Post post = Post.builder().postId(postId).build();
        Member member = Member.builder().memberId(memberId).build();
        Comment parentComment = Comment.builder().commentId(parentCommentId).build();

        // postId, memberId, parentCommentId로 각각 객체 조회 시 Mock 반환
        when(postRepository.findById(postId)).thenReturn(Optional.of(post));
        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));
        when(commentRepository.findById(parentCommentId)).thenReturn(Optional.of(parentComment));

        // 대댓글 객체 생성 및 Mock 설정
        Comment reply = Comment.builder().post(post).member(member).parentComment(parentComment).text(text).build();
        when(commentRepository.save(any(Comment.class))).thenReturn(reply);

        // When: CommentService의 addReply 호출
        Comment result = commentService.addReply(postId, memberId, parentCommentId, text);

        System.out.println("대댓글 추가 결과: " + result); // 추가

        // Then: 반환된 대댓글의 Post, Member, 부모 Comment, Text가 기대값과 일치하는지 확인
        assertThat(result.getPost()).isEqualTo(post);
        assertThat(result.getMember()).isEqualTo(member);
        assertThat(result.getParentComment()).isEqualTo(parentComment);
        assertThat(result.getText()).isEqualTo(text);

        // postRepository, memberRepository, commentRepository의 메서드 호출 검증
        verify(postRepository, times(1)).findById(postId);
        verify(memberRepository, times(1)).findById(memberId);
        verify(commentRepository, times(1)).findById(parentCommentId);
        verify(commentRepository, times(1)).save(any(Comment.class));
    }

    // 특정 댓글의 대댓글 리스트 조회 기능을 테스트하는 메서드
    @Test
    public void whenValidParentCommentId_thenGetReplies() {
        // Given: 가짜 부모 댓글 ID
        Long parentCommentId = 1L;
        Comment parentComment = Comment.builder().commentId(parentCommentId).build();

        // 가짜 대댓글 객체 생성
        Comment reply1 = Comment.builder().commentId(2L).parentComment(parentComment).build();
        Comment reply2 = Comment.builder().commentId(3L).parentComment(parentComment).build();

        // parentCommentId로 부모 댓글 조회 시 Mock 반환
        when(commentRepository.findById(parentCommentId)).thenReturn(Optional.of(parentComment));
        // 부모 댓글에 달린 대댓글 리스트 반환
        when(commentRepository.findByParentComment(parentComment)).thenReturn(Arrays.asList(reply1, reply2));

        System.out.println("대댓글 리스트 조회 시작: parentCommentId=" + parentCommentId); // 추가

        // When: CommentService의 getReplies 호출
        List<Comment> replies = commentService.getReplies(parentCommentId);

        System.out.println("대댓글 조회 결과: " + replies); // 추가

        // Then: 반환된 대댓글 리스트의 크기와 부모 댓글 확인
        assertThat(replies).hasSize(2);
        assertThat(replies.get(0).getParentComment()).isEqualTo(parentComment);
        assertThat(replies.get(1).getParentComment()).isEqualTo(parentComment);

        // 부모 댓글 조회 및 대댓글 리스트 조회 메서드가 1번씩 호출되었는지 검증
        verify(commentRepository, times(1)).findById(parentCommentId);
        verify(commentRepository, times(1)).findByParentComment(parentComment);
    }
}
