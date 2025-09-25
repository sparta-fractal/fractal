package com.sparta.team5.fractal.domain.auth.dto.response;

public record AuthResponse(

        String token
) {
    
    public static AuthResponse from(String token) {
        return new AuthResponse(token);
    }
}
