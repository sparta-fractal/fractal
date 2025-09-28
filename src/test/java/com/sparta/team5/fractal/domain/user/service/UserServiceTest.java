package com.sparta.team5.fractal.domain.user.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import com.sparta.team5.fractal.common.config.PasswordEncoder;
import com.sparta.team5.fractal.common.dto.AuthUser;
import com.sparta.team5.fractal.domain.user.dto.request.UpdatePasswordRequest;
import com.sparta.team5.fractal.domain.user.dto.request.UpdateUserProfileRequest;
import com.sparta.team5.fractal.domain.user.dto.response.UpdatePasswordResponse;
import com.sparta.team5.fractal.domain.user.dto.response.UpdateUserProfileResponse;
import com.sparta.team5.fractal.domain.user.entity.User;
import com.sparta.team5.fractal.domain.user.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

	@Mock
	private UserRepository userRepository;

	@Mock
	private PasswordEncoder passwordEncoder;

	@InjectMocks
	private UserService userService;

	@Test
	@DisplayName("유저 프로필 수정 성공")
	void updateProfile_정상_요청이면_유저_프로필을_수정할_수_있다() {

		// given
		long userId = 1L;

		AuthUser authUser = new AuthUser(userId, "rkatmxm1@a", "Rkatmxm!23");

		User user = User.of("rkatmxm1@a", "Rkatmxm!23", "gamst");
		ReflectionTestUtils.setField(user, "id", userId);

		UpdateUserProfileRequest request = new UpdateUserProfileRequest("rkatmxm123@a", "goodBoy");

		given(userRepository.findById(authUser.id())).willReturn(Optional.of(user));

		// when
		UpdateUserProfileResponse response = userService.updateProfile(authUser, request);

		// then
		assertThat(response.email()).isEqualTo(request.email());
		assertThat(response.nickname()).isEqualTo(request.nickname());

		assertThat(user.getEmail()).isEqualTo(request.email());
		assertThat(user.getNickname()).isEqualTo(request.nickname());
	}

	@Test
	@DisplayName("비밀번호 변경 성공")
	void updatePassword_정상_요청이면_비밀번호를_변경할_수_있다() {

		// given
		long userId = 1L;

		AuthUser authUser = new AuthUser(userId, "rkatmxm1@a", "gamst");

		User user = User.of("rkatmxm1@a", "Rkatmxm!23", "gamst");
		ReflectionTestUtils.setField(user, "id", userId);

		UpdatePasswordRequest request = new UpdatePasswordRequest("Rkatmxm!23", "Rkatmxm!2345");

		given(userRepository.findById(authUser.id())).willReturn(Optional.of(user));
		given(passwordEncoder.matches(request.oldPassword(), user.getPassword())).willReturn(true);
		given(passwordEncoder.encode(request.newPassword())).willReturn("FESfhusfse34wgsdgdsgsg3");

		// when
		UpdatePasswordResponse response = userService.updatePassword(authUser, request);

		// then
		assertThat(user.getPassword()).isEqualTo("FESfhusfse34wgsdgdsgsg3");
	}
}
