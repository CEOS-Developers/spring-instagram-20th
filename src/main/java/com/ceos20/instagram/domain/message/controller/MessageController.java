package com.ceos20.instagram.domain.message.controller;

import com.ceos20.instagram.global.common.annotation.Login;
import com.ceos20.instagram.global.common.response.ApiResponse;
import com.ceos20.instagram.global.common.response.ResponseBuilder;
import com.ceos20.instagram.domain.member.domain.Member;
import com.ceos20.instagram.domain.message.dto.CreateMessageRequest;
import com.ceos20.instagram.domain.message.dto.GetMessageResponse;
import com.ceos20.instagram.domain.message.service.MessageService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/message")
public class MessageController {

    private final MessageService messageService;

    @PostMapping
    public ResponseEntity<ApiResponse<Void>> createMessage(
            @RequestBody CreateMessageRequest createMessageRequest,
            @Login Member member) {

        messageService.createMessage(createMessageRequest, member);
        return ResponseBuilder.createApiResponse("메시지 생성 완료", null);
    }

    @GetMapping("/{chatRoomId}")
    public ResponseEntity<ApiResponse<List<GetMessageResponse>>> getMessageInChatRoom(@PathVariable Long chatRoomId) {

        List<GetMessageResponse> getMessageResponses = messageService.getMessageInChatRoom(chatRoomId);
        return ResponseBuilder.createApiResponse("채팅방의 모든 메시지 조회 완료", getMessageResponses);
    }
}
