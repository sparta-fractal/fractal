package com.sparta.team5.fractal.domain.auth.service;

import com.sparta.team5.fractal.common.config.JwtUtil;
import com.sparta.team5.fractal.common.config.PasswordEncoder;
import com.sparta.team5.fractal.domain.auth.dto.request.AuthRegisterRequest;
import com.sparta.team5.fractal.domain.user.entity.User;
import com.sparta.team5.fractal.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService implements AuthServiceApi {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public void register(AuthRegisterRequest authRegisterRequest) {

        if (userRepository.existsByEmail(authRegisterRequest.email())) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

        String encodedPassword = passwordEncoder.encode(authRegisterRequest.password());

        User user = new User(
                authRegisterRequest.email(),
                encodedPassword,
                authRegisterRequest.nickname()
        );

        userRepository.save(user);
    }
}
