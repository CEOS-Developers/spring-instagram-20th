package com.ceos20.instagram.member.repository;

import com.ceos20.instagram.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
