package com.sparta.team5.fractal.domain.auth.controller;

import com.sparta.team5.fractal.common.response.ApiResponse;
import com.sparta.team5.fractal.domain.auth.dto.request.AuthLoginRequest;
import com.sparta.team5.fractal.domain.auth.dto.request.AuthRegisterRequest;
import com.sparta.team5.fractal.domain.auth.dto.respone.AuthResponse;
import com.sparta.team5.fractal.domain.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<Void>> register(
            @Valid @RequestBody AuthRegisterRequest authRegisterRequest
    ) {

        authService.register(authRegisterRequest);

        return ApiResponse.success(null, "회원가입 성공");
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(
            @RequestBody AuthLoginRequest authLoginRequest
    ) {

        AuthResponse authResponse = authService.login(authLoginRequest);

        return ApiResponse.success(authResponse, "토큰 발급 성공");
    }
}
