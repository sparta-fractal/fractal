package com.sparta.team5.fractal.domain.auth.service;

import com.sparta.team5.fractal.domain.auth.dto.request.AuthLoginRequest;
import com.sparta.team5.fractal.domain.auth.dto.request.AuthRegisterRequest;
import com.sparta.team5.fractal.domain.auth.dto.respone.AuthResponse;

public interface AuthServiceApi {


    void register(AuthRegisterRequest authRegisterRequest);

    AuthResponse login(AuthLoginRequest authLoginRequest);
}
