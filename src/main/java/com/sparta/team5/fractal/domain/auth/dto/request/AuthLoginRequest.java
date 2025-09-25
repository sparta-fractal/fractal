package com.sparta.team5.fractal.domain.auth.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AuthLoginRequest(

        @NotBlank(message = "이메일은 필수입니다.")
        @Size(max = 50, message = "이메일은 50자 이하로 입력해주세요.")
        String email,

        @NotBlank(message = "비밀번호는 필수입니다.")
        String password
) {
}
