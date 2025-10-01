package com.sparta.team5.fractal.common.config.security;

import com.sparta.team5.fractal.common.exception.CommonErrorCode;
import com.sparta.team5.fractal.common.exception.GlobalException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter implements Filter {

    private final JwtUtil jwtUtil;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("JwtFilter init");
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String url = "/api/v1/auth";

        if (httpRequest.getRequestURI().matches(url + "/login") || httpRequest.getRequestURI().matches(url + "/signup")) {
            chain.doFilter(request, response);
            log.info("JwtFilter init");
            return;
        }

        String bearerJwt = httpRequest.getHeader("Authorization");

        if (bearerJwt == null) {
            // 토큰이 없는 경우 401을 반환합니다.
            throw new GlobalException(CommonErrorCode.MISSING_TOKEN);
        }

        String jwt = jwtUtil.substringToken(bearerJwt);

        try {
            // JWT 유효성 검사와 claims 추출
            Claims claims = jwtUtil.extractClaims(jwt);
            if (claims == null) {
                throw new GlobalException(CommonErrorCode.MALFORMED_TOKEN);
            }

            httpRequest.setAttribute("userId", Long.parseLong(claims.getSubject()));
            httpRequest.setAttribute("email", claims.get("email"));
            httpRequest.setAttribute("nickname", claims.get("nickname"));

            chain.doFilter(request, response);
        } catch (SecurityException e) {
            log.error("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.", e);
            throw new GlobalException(CommonErrorCode.INVALID_TOKEN);
        } catch (MalformedJwtException e) {
            log.error("Malformed JWT Token, 잘못된 JWT 토큰 입니다.", e);
            throw new GlobalException(CommonErrorCode.MALFORMED_TOKEN);
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT token, 만료된 JWT token 입니다.", e);
            throw new GlobalException(CommonErrorCode.EXPIRED_TOKEN);
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.", e);
            throw new GlobalException(CommonErrorCode.UNSUPPORTED_TOKEN);
        } catch (Exception e) {
            log.error("Internal server error", e);
            throw new GlobalException(CommonErrorCode.SERVER_ERROR);
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
