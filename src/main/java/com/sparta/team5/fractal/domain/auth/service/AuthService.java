package com.sparta.team5.fractal.domain.auth.service;

import com.sparta.team5.fractal.common.core.dto.AuthUser;
import com.sparta.team5.fractal.common.core.exception.GlobalException;
import com.sparta.team5.fractal.common.infra.security.JwtUtil;
import com.sparta.team5.fractal.common.infra.security.PasswordEncoder;
import com.sparta.team5.fractal.domain.auth.dto.request.AuthLoginRequest;
import com.sparta.team5.fractal.domain.auth.dto.request.AuthRegisterRequest;
import com.sparta.team5.fractal.domain.auth.dto.request.AuthWithdrawRequest;
import com.sparta.team5.fractal.domain.auth.dto.response.AuthResponse;
import com.sparta.team5.fractal.domain.auth.exception.AuthErrorCode;
import com.sparta.team5.fractal.domain.user.entity.User;
import com.sparta.team5.fractal.domain.user.service.UserServiceApi;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService implements AuthServiceApi {

    private final UserServiceApi userServiceApi;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public void register(AuthRegisterRequest authRegisterRequest) {

        if (userServiceApi.existsByEmail(authRegisterRequest.email())) {
            throw new GlobalException(AuthErrorCode.EMAIL_EXIST);
        }

        String encodedPassword = passwordEncoder.encode(authRegisterRequest.password());

        userServiceApi.createUser(
                authRegisterRequest.email(),
                encodedPassword,
                authRegisterRequest.nickname()
        );
    }

    @Override
    public AuthResponse login(AuthLoginRequest authLoginRequest) {

        User user = userServiceApi.findByEmail(authLoginRequest.email()).orElse(null);

        // 로그인 시 이메일과 비밀번호가 일치하지 않을 경우 401을 반환합니다.
        if (user == null || !passwordEncoder.matches(authLoginRequest.password(), user.getPassword())) {
            throw new GlobalException(AuthErrorCode.EMAIL_OR_PASSWORD_MISMATCH);
        }

        String bearerToken = jwtUtil.createToken(user.getId(), user.getEmail(), user.getNickname());

        return AuthResponse.from(bearerToken);
    }

    @Override
    public void withdraw(AuthUser authUser, AuthWithdrawRequest authWithdrawRequest) {

        User user = userServiceApi.findByEmail(authUser.email()).orElseThrow(
                () -> new GlobalException(AuthErrorCode.EMAIL_NOT_EXIST)
        );

        if (!passwordEncoder.matches(authWithdrawRequest.password(), user.getPassword())) {
            throw new GlobalException(AuthErrorCode.PASSWORD_MISMATCH);
        }

        user.delete();
    }
}
