package com.sparta.team5.fractal.domain.user.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateUserProfileRequest(

	@NotBlank(message = "이메일은 필수입니다.")
	@Email(message = "유효한 이메일 주소를 입력해주세요.")
	@Size(max = 50, message = "이메일은 50자 이하로 입력해주세요.")
	String email,

	@NotBlank(message = "이름은 필수입니다.")
	@Size(min = 2, max = 20, message = "이름은 2자 이상 20자 이하로 입력해주세요.")
	String nickname) {
}
