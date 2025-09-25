package com.sparta.team5.fractal.domain.user.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sparta.team5.fractal.common.annotation.Auth;
import com.sparta.team5.fractal.common.dto.AuthUser;
import com.sparta.team5.fractal.common.exception.GlobalException;
import com.sparta.team5.fractal.domain.user.dto.request.UpdateUserProfileRequest;
import com.sparta.team5.fractal.domain.user.dto.response.UpdateUserProfileResponse;
import com.sparta.team5.fractal.domain.user.entity.User;
import com.sparta.team5.fractal.domain.user.exception.UserErrorCode;
import com.sparta.team5.fractal.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService implements UserServiceApi {
	private final UserRepository userRepository;

	@Transactional
	public UpdateUserProfileResponse updateProfile(@Auth AuthUser authUser, UpdateUserProfileRequest request) {

		User user = userRepository.findById(
			authUser.id()).orElseThrow(() -> new GlobalException(UserErrorCode.USER_NOT_FOUND));

		user.changeProfile(request.email(), request.nickname());

		User savedUser = userRepository.save(user);

		return UpdateUserProfileResponse.from(savedUser);
	}

	@Override
	@Transactional(readOnly = true)
	public boolean existsByEmail(String email) {

		return userRepository.existsByEmail(email);
	}

	@Override
	@Transactional
	public User createUser(String email, String password, String nickname) {

		User user = User.of(email, password, nickname);

		return userRepository.save(user);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<User> findByEmail(String email) {

		return userRepository.findByEmail(email);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<User> findById(Long id) {

		return userRepository.findById(id);
	}
}
