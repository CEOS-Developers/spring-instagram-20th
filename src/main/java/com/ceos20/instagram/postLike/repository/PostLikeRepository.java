package com.ceos20.instagram.postLike.repository;

import com.ceos20.instagram.post.domain.Post;
import com.ceos20.instagram.postLike.domain.PostLike;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostLikeRepository {

    public final EntityManager em;

    // 게시글 좋아요 저장
    public void save(PostLike postLike) {
        em.persist(postLike);
    }

    // 특정 게시글에 남겨진 좋아요 조회
    public List<PostLike> findByPost(Post post) {
        return em.createQuery("select pl from PostLike pl where pl.post = :post", PostLike.class)
                .setParameter("post", post)
                .getResultList();
    }
}
