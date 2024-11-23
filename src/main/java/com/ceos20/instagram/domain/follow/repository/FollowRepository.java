package com.ceos20.instagram.domain.follow.repository;

import com.ceos20.instagram.domain.follow.domain.Follow;
import com.ceos20.instagram.domain.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    // toUser를 팔로우하는 사람들(fromUser) 리스트 반환
    @Query("SELECT f.fromMember FROM Follow f WHERE f.toMember = :toMember")
    List<Member> findByToMember(@Param("toMember") Member toMember);

    // fromUser가 팔로우하는 사람들(toUser) 리스트 반환
    @Query("SELECT f.toMember FROM Follow f WHERE f.fromMember = :fromMember")
    List<Member> findByFromMember(@Param("fromMember") Member fromMember);

    // FromUser과 ToUser로 follow 찾기
    Follow findByFromMemberAndToMember(Member fromMember, Member toMember);
}
