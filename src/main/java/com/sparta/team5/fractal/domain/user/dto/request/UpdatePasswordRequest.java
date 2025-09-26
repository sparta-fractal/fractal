package com.sparta.team5.fractal.domain.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UpdatePasswordRequest(

	@NotBlank(message = "비밀번호는 필수입니다.")
	String oldPassword,

	@NotBlank(message = "비밀번호는 필수입니다.")
	@Size(min = 8, message = "비밀번호는 8자 이상이어야 합니다.")
	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[!@#$%^&()_+\\-=\\[\\]{};':\"\\\\|,.<>/?]).*$", message = "비밀번호는 영문, 숫자, 특수문자를 모두 포함해야 합니다.")
	String newPassword) {
}
