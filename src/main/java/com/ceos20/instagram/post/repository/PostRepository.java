package com.ceos20.instagram.post.repository;

import com.ceos20.instagram.post.domain.Post;
import com.ceos20.instagram.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByAuthor(User author);

    @Query("SELECT p FROM Post p LEFT JOIN fetch p.author")
    List<Post> findAllPost();
}
