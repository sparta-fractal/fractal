package com.sparta.team5.fractal.domain.auth.exception;

import com.sparta.team5.fractal.common.core.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum AuthErrorCode implements ErrorCode {

    EMAIL_EXIST("EMAIL_EXIST", HttpStatus.BAD_REQUEST, "이미 존재하는 이메일입니다."),
    EMAIL_NOT_EXIST("EMAIL_NOT_EXIST", HttpStatus.BAD_REQUEST, "이메일이 존재하지 않습니다."),
    PASSWORD_MISMATCH("PASSWORD_MISMATCH", HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다."),
    EMAIL_OR_PASSWORD_MISMATCH("EMAIL_OR_PASSWORD_MISMATCH", HttpStatus.UNAUTHORIZED, "이메일 또는 비밀번호가 일치하지 않습니다.");

    private final String code;
    private final HttpStatus httpStatus;
    private final String message;
}
