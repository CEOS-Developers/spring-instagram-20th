package com.ceos20.instagram.post.repository;

import com.ceos20.instagram.post.domain.Post;
import com.ceos20.instagram.user.domain.User;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostRepository {

    private final EntityManager em;

    // 게시글 저장
    public void save(Post post) {
        em.persist(post);
    }

    // id로 게시글 조회
    public Post findById(Long id) {
        return em.find(Post.class, id);
    }

    // author로 게시글 조회
    public List<Post> findByAuthor(User user) {
        return em.createQuery("select p from Post p where p.author = :author", Post.class)
                .setParameter("author", user)
                .getResultList();
    }
}
