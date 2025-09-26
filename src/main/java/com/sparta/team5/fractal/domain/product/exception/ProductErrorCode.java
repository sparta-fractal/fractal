package com.sparta.team5.fractal.domain.product.exception;

import com.sparta.team5.fractal.common.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ProductErrorCode implements ErrorCode {

    PRODUCT_NOT_FOUND("PRODUCT_NOT_FOUND", HttpStatus.NOT_FOUND, "상품을 찾을 수 없습니다."),
    INVALID_PRODUCT_DATA("INVALID_PRODUCT_DATA", HttpStatus.BAD_REQUEST, "유효하지 않은 상품 데이터입니다.");

    private final String code;
    private final HttpStatus httpStatus;
    private final String message;
}
