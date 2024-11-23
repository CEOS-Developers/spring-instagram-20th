package com.ceos20.instagram.domain.chatRoom.controller;

import com.ceos20.instagram.domain.chatRoom.dto.GetChatRoomIdResponse;
import com.ceos20.instagram.domain.chatRoom.service.ChatRoomService;
import com.ceos20.instagram.global.common.annotation.Login;
import com.ceos20.instagram.global.common.response.ApiResponse;
import com.ceos20.instagram.global.common.response.ResponseBuilder;
import com.ceos20.instagram.domain.member.domain.Member;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/chatroom")
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    @PostMapping("/friend/{friendId}")
    public ResponseEntity<ApiResponse<Void>> createChatRoom(
            @PathVariable Long friendId,
            @Login Member member) {

        chatRoomService.createChatRoom(member.getId(), friendId);
        return ResponseBuilder.createApiResponse("채팅방 생성 완료", null);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<GetChatRoomIdResponse>>> getMyChatRoomIds(@Login Member member) {

        List<GetChatRoomIdResponse> getChatRoomIdResponses = chatRoomService.getMyChatRoomIds(member);
        return ResponseBuilder.createApiResponse("나의 모든 채팅방 id 조회 완료", getChatRoomIdResponses);
    }
}
