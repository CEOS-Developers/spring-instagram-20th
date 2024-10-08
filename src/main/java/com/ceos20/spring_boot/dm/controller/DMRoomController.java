package com.ceos20.spring_boot.dm.controller;

import com.ceos20.spring_boot.dm.domain.DMRoom;
import com.ceos20.spring_boot.dm.service.DMRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dmrooms")
public class DMRoomController {

    private final DMRoomService dmRoomService;

    @Autowired
    public DMRoomController(DMRoomService dmRoomService) {
        this.dmRoomService = dmRoomService;
    }

    // DMRoom 생성 (POST 요청)
    @PostMapping
    public ResponseEntity<DMRoom> createDMRoom(@RequestBody DMRoom dmRoom) {
        DMRoom createdDMRoom = dmRoomService.createDMRoom(dmRoom);
        return ResponseEntity.ok(createdDMRoom);
    }

    // DMRoom 조회 (GET 요청)
    @GetMapping("/{dmRoomId}")
    public ResponseEntity<DMRoom> getDMRoomById(@PathVariable Long dmRoomId) {
        DMRoom dmRoom = dmRoomService.getDMRoomById(dmRoomId);
        return ResponseEntity.ok(dmRoom);
    }

    // DMRoom 삭제 (DELETE 요청)
    @DeleteMapping("/{dmRoomId}")
    public ResponseEntity<Void> deleteDMRoom(@PathVariable Long dmRoomId) {
        dmRoomService.deleteDMRoom(dmRoomId);
        return ResponseEntity.noContent().build();
    }
}
