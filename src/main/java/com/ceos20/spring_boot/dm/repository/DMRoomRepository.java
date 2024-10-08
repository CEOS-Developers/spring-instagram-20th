package com.ceos20.spring_boot.dm.repository;

import com.ceos20.spring_boot.dm.domain.DMRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DMRoomRepository extends JpaRepository<DMRoom, Long> {

    // 필요에 따라 추가 메서드 작성 가능
}
