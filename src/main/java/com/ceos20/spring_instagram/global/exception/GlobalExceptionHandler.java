package com.ceos20.spring_instagram.global.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j // SLF4J 어노테이션을 사용하여 Logger 생성
public class GlobalExceptionHandler {

    @ExceptionHandler(CommentNotFoundException.class)
    public ResponseEntity<String> handleCommentNotFound(CommentNotFoundException ex) {
        return handleException(ex, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<String> handlePostNotFound(PostNotFoundException ex) {
        return handleException(ex, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MemberNotFoundException.class)
    public ResponseEntity<String> handleMemberNotFound(MemberNotFoundException ex) {
        return handleException(ex, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class) // 일반적인 예외 처리
    public ResponseEntity<String> handleAllExceptions(Exception ex) {
        log.error("서버 오류 발생: ", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류가 발생했습니다.");
    }

    // 공통 예외 처리 메서드
    private ResponseEntity<String> handleException(Exception ex, HttpStatus status) {
        log.warn("예외 발생: ", ex); // 경고 로그
        return ResponseEntity.status(status).body(ex.getMessage());
    }
}


