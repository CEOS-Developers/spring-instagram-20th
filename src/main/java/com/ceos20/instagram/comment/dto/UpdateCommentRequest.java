package com.ceos20.instagram.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCommentRequest {

    private String content;

    private Long commentId;
}
