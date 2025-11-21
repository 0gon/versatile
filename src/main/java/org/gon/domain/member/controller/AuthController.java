package org.gon.domain.member.controller;

import io.jsonwebtoken.Claims;
import jakarta.annotation.security.PermitAll;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.gon.comm.JwtUtil;
import org.gon.domain.ResultTypeCode;
import org.gon.domain.member.service.RefreshTokenBlacklistService;
import org.gon.domain.response.Response;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;
import java.util.Date;


@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtUtil jwtUtil;
    private final RefreshTokenBlacklistService refreshTokenBlacklistService;

    @PermitAll
    @PostMapping("/refresh")
    public Response<String> refresh(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        String refreshToken = null;
        if (cookies == null) {
            throw new RuntimeException("리프레시 토큰이 없습니다.");
        }
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("refreshToken")) {
                refreshToken = cookie.getValue();
                break;
            }
        }

        if(!jwtUtil.validateToken(refreshToken)) {
            throw new RuntimeException("리프레시 토큰이 유효하지 않습니다.");
        }
        if(refreshTokenBlacklistService.isBlacklisted(refreshToken)) {
           throw new RuntimeException("리프레시 토큰이 블랙리스트에 등록되어 있습니다.");
        }

        Claims claims = jwtUtil.getClaims(refreshToken);
        String memberId = claims.getSubject();

        // 기존 리프레시 토큰 블랙리스트에 추가
        ZonedDateTime now = ZonedDateTime.now();
        Date expiration = claims.getExpiration();
        long expirationMillis = expiration.getTime() - now.toInstant().toEpochMilli();
        refreshTokenBlacklistService.blacklistToken(refreshToken, expirationMillis);

        // 리프레시 토큰 재발급
        String newRefreshToken = jwtUtil.createRefreshToken(Long.parseLong(memberId));
        Cookie newCookie = new Cookie("refreshToken", newRefreshToken);
        newCookie.setHttpOnly(true);
        newCookie.setPath("/");
        newCookie.setMaxAge((int) JwtUtil.REFRESH_TOKEN_EXP_TIME);
        response.addCookie(newCookie);
        response.addCookie(newCookie);

        // 새로운 액세스 토큰 반환
        String newAccessToken = jwtUtil.createAccessToken(Long.parseLong(memberId));
        return Response.of(ResultTypeCode.SUCCESS, true, "토큰 재발급 성공", newAccessToken);
    }
}
