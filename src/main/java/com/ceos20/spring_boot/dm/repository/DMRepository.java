package com.ceos20.spring_boot.dm.repository;

import com.ceos20.spring_boot.dm.domain.DM;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DMRepository extends JpaRepository<DM, Long> {

    // 필요에 따라 추가 메서드 작성 가능
}
