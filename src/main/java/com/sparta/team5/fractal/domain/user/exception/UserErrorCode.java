package com.sparta.team5.fractal.domain.user.exception;

import com.sparta.team5.fractal.common.core.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum UserErrorCode implements ErrorCode {

    USER_NOT_FOUND("USER_NOT_FOUND", HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."),
    DUPLICATE_EMAIL("DUPLICATE_EMAIL", HttpStatus.CONFLICT, "이미 사용 중인 이메일입니다."),
    PASSWORD_SAME_AS_OLD("PASSWORD_SAME_AS_OLD", HttpStatus.BAD_REQUEST, "새 비밀번호는 기존 비밀번호와 같을 수 없습니다."),
    INVALID_OLD_PASSWORD("INVALID_OLD_PASSWORD", HttpStatus.BAD_REQUEST, "기존 비밀번호가 올바르지 않습니다.");

    private final String code;
    private final HttpStatus httpStatus;
    private final String message;
}
