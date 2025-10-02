package com.sparta.team5.fractal.common.core.exception;

import org.springframework.http.HttpStatus;

public interface ErrorCode {

    String getCode();

    HttpStatus getHttpStatus();

    String getMessage();
}