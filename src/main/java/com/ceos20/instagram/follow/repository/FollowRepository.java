package com.ceos20.instagram.follow.repository;

import com.ceos20.instagram.follow.domain.Follow;
import com.ceos20.instagram.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    // toUser를 팔로우하는 사람들(fromUser) 리스트 반환
    @Query("SELECT f.fromUser FROM Follow f WHERE f.toUser = :toUser")
    List<User> findByToUser(@Param("toUser") User toUser);

    // fromUser가 팔로우하는 사람들(toUser) 리스트 반환
    @Query("SELECT f.toUser FROM Follow f WHERE f.fromUser = :fromUser")
    List<User> findByFromUser(@Param("fromUser") User fromUser);

    // FromUser과 ToUser로 follow 찾기
    Follow findByFromUserAndToUser(User fromUser, User toUser);
}
