package com.ceos20.instagram.domain.post.repository;

import com.ceos20.instagram.domain.post.domain.Post;
import com.ceos20.instagram.domain.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByAuthor(Member author);

    @Query("SELECT p FROM Post p LEFT JOIN fetch p.author")
    List<Post> findAllPost();
}
