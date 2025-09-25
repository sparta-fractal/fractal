package com.sparta.team5.fractal.domain.auth.service;

import com.sparta.team5.fractal.domain.auth.dto.request.AuthRegisterRequest;

public interface AuthServiceApi {


    void register(AuthRegisterRequest authRegisterRequest);
}
