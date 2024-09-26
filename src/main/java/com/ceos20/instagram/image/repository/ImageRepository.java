package com.ceos20.instagram.image.repository;

import com.ceos20.instagram.image.domain.Image;
import com.ceos20.instagram.post.domain.Post;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ImageRepository {

    private final EntityManager em;

    // 이미지 저장
    public void save(Image image) {
        em.persist(image);
    }

    // 특정 게시글의 이미지 조회
    public List<Image> findByPost(Post post) {
        return em.createQuery("select i from Image i where i.post = :post", Image.class)
                .setParameter("post", post)
                .getResultList();
    }
}
