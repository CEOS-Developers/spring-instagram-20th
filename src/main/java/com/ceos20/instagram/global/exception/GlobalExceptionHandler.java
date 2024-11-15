package com.ceos20.instagram.global.exception;

import com.ceos20.instagram.common.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleNotFoundException(final NotFoundException e) {

        ExceptionResponse exceptionResponse = ExceptionResponse.from(e.getStatus(), e.getMessage());
        ApiResponse<Void> apiResponse = ApiResponse.failure(exceptionResponse);

        return ResponseEntity
                .status(exceptionResponse.getStatus())
                .body(apiResponse);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ApiResponse<Void>> handleConflictException(final ConflictException e) {

        ExceptionResponse exceptionResponse = ExceptionResponse.from(e.getStatus(), e.getMessage());
        ApiResponse<Void> apiResponse = ApiResponse.failure(exceptionResponse);

        return ResponseEntity
                .status(exceptionResponse.getStatus())
                .body(apiResponse);
    }
}
