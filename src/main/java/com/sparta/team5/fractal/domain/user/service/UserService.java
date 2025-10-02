package com.sparta.team5.fractal.domain.user.service;

import com.sparta.team5.fractal.common.core.dto.AuthUser;
import com.sparta.team5.fractal.common.core.exception.GlobalException;
import com.sparta.team5.fractal.common.crosscutting.annotation.Auth;
import com.sparta.team5.fractal.common.infra.security.PasswordEncoder;
import com.sparta.team5.fractal.domain.user.dto.request.UpdatePasswordRequest;
import com.sparta.team5.fractal.domain.user.dto.request.UpdateUserProfileRequest;
import com.sparta.team5.fractal.domain.user.dto.response.UpdatePasswordResponse;
import com.sparta.team5.fractal.domain.user.dto.response.UpdateUserProfileResponse;
import com.sparta.team5.fractal.domain.user.entity.User;
import com.sparta.team5.fractal.domain.user.exception.UserErrorCode;
import com.sparta.team5.fractal.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserServiceApi {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UpdateUserProfileResponse updateProfile(@Auth AuthUser authUser, UpdateUserProfileRequest request) {

        User user = userRepository.findById(
                authUser.id()).orElseThrow(() -> new GlobalException(UserErrorCode.USER_NOT_FOUND));

        if (!user.getEmail().equals(request.email()) && userRepository.existsByEmail(request.email())) {
            throw new GlobalException(UserErrorCode.DUPLICATE_EMAIL);
        }

        user.changeProfile(request.email(), request.nickname());

        return UpdateUserProfileResponse.from(user);
    }

    @Transactional
    public UpdatePasswordResponse updatePassword(AuthUser authUser, UpdatePasswordRequest request) {

        User user = userRepository.findById(
                authUser.id()).orElseThrow(() -> new GlobalException(UserErrorCode.USER_NOT_FOUND));

        if (!passwordEncoder.matches(request.oldPassword(), user.getPassword())) {
            throw new GlobalException(UserErrorCode.INVALID_OLD_PASSWORD);
        }

        if (passwordEncoder.matches(request.newPassword(), user.getPassword())) {
            throw new GlobalException(UserErrorCode.PASSWORD_SAME_AS_OLD);
        }

        user.changePassword(passwordEncoder.encode(request.newPassword()));

        return UpdatePasswordResponse.from(user);
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
