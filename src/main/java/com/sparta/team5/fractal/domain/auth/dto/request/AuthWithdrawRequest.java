package com.sparta.team5.fractal.domain.auth.dto.request;

import jakarta.validation.constraints.NotBlank;

public record AuthWithdrawRequest(

        @NotBlank(message = "비밀번호는 필수입니다.")
        String password
) {
}
