package com.sparta.team5.fractal.domain.user.exception;

import org.springframework.http.HttpStatus;

import com.sparta.team5.fractal.common.exception.ErrorCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserErrorCode implements ErrorCode {
	USER_NOT_FOUND("USR-404", HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다.");

	private final String code;
	private final HttpStatus httpStatus;
	private final String message;

}
