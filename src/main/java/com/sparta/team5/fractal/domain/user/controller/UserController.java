package com.sparta.team5.fractal.domain.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.team5.fractal.common.annotation.Auth;
import com.sparta.team5.fractal.common.dto.AuthUser;
import com.sparta.team5.fractal.common.response.ApiResponse;
import com.sparta.team5.fractal.domain.user.constant.SuccessMessages;
import com.sparta.team5.fractal.domain.user.dto.request.UpdatePasswordRequest;
import com.sparta.team5.fractal.domain.user.dto.request.UpdateUserProfileRequest;
import com.sparta.team5.fractal.domain.user.dto.response.UpdatePasswordResponse;
import com.sparta.team5.fractal.domain.user.dto.response.UpdateUserProfileResponse;
import com.sparta.team5.fractal.domain.user.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users/me")
public class UserController {

	private final UserService userService;

	@PutMapping
	public ResponseEntity<ApiResponse<UpdateUserProfileResponse>> updateProfile(
		@Auth AuthUser authUser,
		@Valid @RequestBody UpdateUserProfileRequest request) {

		UpdateUserProfileResponse response = userService.updateProfile(authUser, request);

		return ApiResponse.success(response, SuccessMessages.PROFILE_UPDATE_SUCCESS);
	}

	@PutMapping("/password")
	public ResponseEntity<ApiResponse<UpdatePasswordResponse>> updatePassword(
		@Auth AuthUser authUser,
		@Valid @RequestBody UpdatePasswordRequest request) {

		UpdatePasswordResponse response = userService.updatePassword(authUser, request);

		return ApiResponse.success(response, SuccessMessages.PASSWORD_UPDATE_SUCCESS);
	}

}
