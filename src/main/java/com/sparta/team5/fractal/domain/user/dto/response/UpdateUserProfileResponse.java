package com.sparta.team5.fractal.domain.user.dto.response;

import java.time.LocalDateTime;

import com.sparta.team5.fractal.domain.user.entity.User;

public record UpdateUserProfileResponse(Long id,
										String email,
										String nickname,
										LocalDateTime createdAt,
										LocalDateTime updatedAt) {
	public static UpdateUserProfileResponse from(User user) {

		return new UpdateUserProfileResponse(
			user.getId(),
			user.getEmail(),
			user.getNickname(),
			user.getCreatedAt(),
			user.getUpdatedAt());
	}
}
