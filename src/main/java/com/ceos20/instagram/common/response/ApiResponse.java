package com.ceos20.instagram.common.response;

import com.ceos20.instagram.global.exception.ExceptionResponse;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ApiResponse<T> {

    private static final String SUCCESS_STATUS = "success";
    private static final String FAIL_STATUS = "fail";
    private static final String ERROR_STATUS = "error";

    private final String status;
    private final String message;
    private final T data;

    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(SUCCESS_STATUS, message, data);
    }

    public static <T> ApiResponse<T> failure(ExceptionResponse error) {
        return new ApiResponse<>(ERROR_STATUS, error.getMessage(), null);
    }
}
