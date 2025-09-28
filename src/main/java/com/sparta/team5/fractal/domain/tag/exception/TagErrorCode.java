package com.sparta.team5.fractal.domain.tag.exception;

import com.sparta.team5.fractal.common.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum TagErrorCode implements ErrorCode {


    TAG_NOT_FOUND("TAG_NOT_FOUND", HttpStatus.NOT_FOUND, "태그를 찾을 수 없습니다.");

    private final String code;
    private final HttpStatus httpStatus;
    private final String message;
}
