package com.ceos20.instagram.image.repository;

import com.ceos20.instagram.image.domain.Image;
import com.ceos20.instagram.post.domain.Post;
import com.ceos20.instagram.user.domain.User;
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
public class ImageRepositoryTest {

    private final ImageRepository imageRepository;
    private final EntityManager em;

    @Autowired
    public ImageRepositoryTest(ImageRepository imageRepository, EntityManager em) {
        this.imageRepository = imageRepository;
        this.em = em;
    }

    @Test
    @DisplayName("특정 게시글의 이미지 조회 테스트")
    public void findByPostTest() {
        // given
        User user = User.builder()
                .username("user")
                .nickname("user")
                .password("password")
                .build();
        em.persist(user);

        Post post = Post.builder()
                .author(user)
                .content("post")
                .build();
        em.persist(post);

        Image image1 = Image.builder()
                .imageUrl("image1_url")
                .index(1L)
                .post(post)
                .build();
        em.persist(image1);

        Image image2 = Image.builder()
                .imageUrl("image2_url")
                .index(2L)
                .post(post)
                .build();
        em.persist(image2);

        // when
        List<Image> findImages = imageRepository.findByPost(post);

        // then
        assertEquals(2, findImages.size());

        for(Image findImage: findImages) {
            System.out.println("url: "+ findImage.getImageUrl());
        }
    }
}
