package com.ceos20.spring_boot.dm.service;

import com.ceos20.spring_boot.dm.domain.DMRoom;
import com.ceos20.spring_boot.dm.dto.DMRoomRequestDto;
import com.ceos20.spring_boot.dm.dto.DMRoomResponseDto;
import com.ceos20.spring_boot.dm.repository.DMRoomRepository;
import com.ceos20.spring_boot.user.domain.User;
import com.ceos20.spring_boot.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DMRoomService {

    private final DMRoomRepository dmRoomRepository;
    private final UserRepository userRepository;

    @Autowired
    public DMRoomService(DMRoomRepository dmRoomRepository, UserRepository userRepository) {
        this.dmRoomRepository = dmRoomRepository;
        this.userRepository = userRepository;
    }

    // DMRoom 생성
    @Transactional
    public DMRoomResponseDto createDMRoom(DMRoomRequestDto dmRoomRequestDto) {
        User user1 = userRepository.findById(dmRoomRequestDto.getUserId1())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + dmRoomRequestDto.getUserId1()));
        User user2 = userRepository.findById(dmRoomRequestDto.getUserId2())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + dmRoomRequestDto.getUserId2()));

        DMRoom dmRoom = DMRoomRequestDto.toEntity(dmRoomRequestDto, user1, user2);
        DMRoom savedDMRoom = dmRoomRepository.save(dmRoom);
        return DMRoomResponseDto.fromEntity(savedDMRoom);
    }

    // 특정 DMRoom 조회
    @Transactional(readOnly = true)
    public DMRoomResponseDto getDMRoomById(Long dmRoomId) {
        DMRoom dmRoom = dmRoomRepository.findById(dmRoomId)
                .orElseThrow(() -> new RuntimeException("DM Room not found with id: " + dmRoomId));
        return DMRoomResponseDto.fromEntity(dmRoom);
    }

    // DMRoom 삭제
    @Transactional
    public void deleteDMRoom(Long dmRoomId) {
        if (!dmRoomRepository.existsById(dmRoomId)) {
            throw new RuntimeException("DM Room not found with id: " + dmRoomId);
        }
        dmRoomRepository.deleteById(dmRoomId);
    }
}
