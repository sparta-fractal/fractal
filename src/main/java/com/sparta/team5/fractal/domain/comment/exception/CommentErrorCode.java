package com.sparta.team5.fractal.domain.comment.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CommentErrorCode {
    COMMENT_NOT_FOUND("COMMENT_NOT_FOUND", HttpStatus.NOT_FOUND, "댓글을 찾을 수 없습니다.");

    private final String code;
    private final HttpStatus httpStatus;
    private final String message;
}
