package com.sparta.team5.fractal.domain.category.exception;

import com.sparta.team5.fractal.common.core.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public enum CategoryErrorCode implements ErrorCode {
    CATEGORY_NOT_FOUND("CATEGORY_001", HttpStatus.NOT_FOUND, "카테고리를 찾을 수 없습니다."),
    CATEGORY_NAME_DUPLICATED("CATEGORY_002", HttpStatus.CONFLICT, "이미 존재하는 카테고리명입니다.");

    private final String code;
    private final HttpStatus httpStatus;
    private final String message;

    CategoryErrorCode(String code, HttpStatus httpStatus, String message) {
        this.code = code;
        this.httpStatus = httpStatus;
        this.message = message;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    @Override
    public String getMessage() {
        return message;
    }
}