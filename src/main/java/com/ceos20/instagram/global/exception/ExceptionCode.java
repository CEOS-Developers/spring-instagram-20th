package com.ceos20.instagram.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ExceptionCode {

    // 400
    MALFORMED_TOKEN(HttpStatus.BAD_REQUEST, "잘못된 JWT 형식입니다."),
    ILLEGAL_ARGUMENT(HttpStatus.BAD_REQUEST, "토큰이 비어있거나 잘못된 형식입니다."),
    NOT_FOUND_REFRESH_TOKEN(HttpStatus.BAD_REQUEST, "refreshToken 쿠키가 존재하지 않습니다."),


    // 401
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "토큰이 만료되었습니다."),
    INVALID_SIGNATURE(HttpStatus.UNAUTHORIZED, "서명이 유효하지 않습니다."),

    // 404
    NOT_FOUND_MEMBER(HttpStatus.NOT_FOUND, "해당 member는 존재하지 않습니다."),
    NOT_FOUND_POST(HttpStatus.NOT_FOUND, "해당 post는 존재하지 않습니다."),
    NOT_FOUND_COMMENT(HttpStatus.NOT_FOUND, "해당 comment는 존재하지 않습니다."),
    NOT_FOUND_IMAGE(HttpStatus.NOT_FOUND, "해당 image는 존재하지 않습니다."),
    NOT_FOUND_POSTLIKE(HttpStatus.NOT_FOUND, "해당 postlike는 존재하지 않습니다."),
    NOT_FOUND_CHATROOM(HttpStatus.NOT_FOUND, "해당 chatroom은 존재하지 않습니다."),
    NOT_FOUND_MESSAGE(HttpStatus.NOT_FOUND, "해당 message는 존재하지 않습니다."),

    // 409
    ALREADY_EXISTS_NICKNAME(HttpStatus.CONFLICT, "이미 존재하는 nickname 입니다."),
    ALREADY_EXISTS_MEMBER(HttpStatus.CONFLICT, "이미 존재하는 member 입니다.");

    private final HttpStatus status;
    private final String message;

    ExceptionCode(final HttpStatus status, final String message) {
        this.status = status;
        this.message = message;
    }
}
