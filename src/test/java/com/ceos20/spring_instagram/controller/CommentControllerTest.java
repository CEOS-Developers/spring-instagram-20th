package com.ceos20.spring_instagram.controller;

import com.ceos20.spring_instagram.domain.Member;
import com.ceos20.spring_instagram.domain.Post;
import com.ceos20.spring_instagram.domain.Comment;
import com.ceos20.spring_instagram.repository.CommentRepository;
import com.ceos20.spring_instagram.repository.MemberRepository;
import com.ceos20.spring_instagram.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private MemberRepository memberRepository;

    private Long postId;
    private Long memberId;
    private Long commentId;

    @BeforeEach
    public void setup() {
        // 테스트 전에 필요한 데이터베이스 초기화
        commentRepository.deleteAll();
        postRepository.deleteAll();
        memberRepository.deleteAll();

        Member member = Member.builder()
                .membername("testUser")
                .email("test@example.com")
                .password("password")
                .build();
        memberId = memberRepository.save(member).getMemberId();

        Post post = Post.builder()
                .member(member)
                .image("test.jpg")
                .caption("테스트 포스트")
                .build();
        postId = postRepository.save(post).getPostId();

        // 테스트용 댓글 추가
        Comment comment = Comment.builder()
                .post(post)
                .member(member)
                .text("테스트 댓글입니다.")
                .build();

        // 댓글을 실제로 추가하여 commentId를 저장
        commentId = commentRepository.save(comment).getCommentId();
    }

    @Test
    public void testCreateComment() throws Exception {
        // 댓글 생성 API 테스트
        String json = "새로운 테스트 댓글입니다.";

        mockMvc.perform(post("/api/comments/?postId=" + postId + "&memberId=" + memberId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.text").value("새로운 테스트 댓글입니다."));
    }

    @Test
    public void testGetAllCommentsByPost() throws Exception {
        // 특정 포스트에 대한 모든 댓글 가져오기 API 테스트
        mockMvc.perform(get("/api/comments/post/{postId}", postId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    public void testGetCommentById() throws Exception {
        // 댓글을 추가한 후 해당 댓글의 ID를 사용하여 조회 테스트
        mockMvc.perform(get("/api/comments/{commentId}", commentId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.commentId").value(commentId));
    }

    @Test
    public void testDeleteComment() throws Exception {
        // 댓글을 추가한 후 해당 댓글의 ID를 사용하여 삭제 테스트
        mockMvc.perform(delete("/api/comments/{commentId}", commentId))
                .andExpect(status().isNoContent());
    }
}
