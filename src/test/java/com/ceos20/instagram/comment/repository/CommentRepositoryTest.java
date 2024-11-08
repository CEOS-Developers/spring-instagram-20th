package com.ceos20.instagram.comment.repository;

import com.ceos20.instagram.comment.domain.Comment;
import com.ceos20.instagram.post.domain.Post;
import com.ceos20.instagram.member.domain.Member;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
public class CommentRepositoryTest {

    private final CommentRepository commentRepository;
    private final EntityManager em;

    @Autowired
    public CommentRepositoryTest(CommentRepository commentRepository, EntityManager em) {
        this.commentRepository = commentRepository;
        this.em = em;
    }

    @Test
    @DisplayName("특정 게시글의 댓글 조회 테스트")
    public void findByPostTest() {
        // given
        Member member = Member.builder()
                .username("user")
                .nickname("user")
                .password("password")
                .build();
        em.persist(member);

        Post post = Post.builder()
                .content("post")
                .author(member)
                .build();
        em.persist(post);

        Comment comment1 = Comment.builder()
                .author(member)
                .content("첫번째 댓글")
                .post(post)
                .build();
        em.persist(comment1);

        Comment comment2 = Comment.builder()
                .author(member)
                .content("두번째 댓글")
                .post(post)
                .parentComment(comment1)
                .build();
        em.persist(comment2);

        // when
        List<Comment> findComments = commentRepository.findByPost(post);

        // then
        assertEquals(2, findComments.size());

        for(Comment findComment: findComments) {
            System.out.println("comment: "+findComment.getContent());
        }
    }

}
