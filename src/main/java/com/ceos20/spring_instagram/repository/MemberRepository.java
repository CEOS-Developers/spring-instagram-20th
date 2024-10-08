package com.ceos20.spring_instagram.repository;

import com.ceos20.spring_instagram.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;


@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByMembername(String membername);
}