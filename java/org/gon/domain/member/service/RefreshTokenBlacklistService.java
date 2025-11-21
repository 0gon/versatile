package org.gon.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RefreshTokenBlacklistService {

    private final RedisTemplate<String, String> redisTemplate; // StringRedisTemplate 사용 권장

    // 블랙리스트 키 접두사
    private static final String BLACKLIST_PREFIX = "blacklist:";

    /**
     * Refresh Token을 블랙리스트에 저장합니다.
     * @param refreshToken 블랙리스트에 추가할 Refresh Token
     * @param expirationTimeMillis 토큰의 남은 유효 시간 (밀리초)
     */
    public void blacklistToken(String refreshToken, long expirationTimeMillis) {
        String key = BLACKLIST_PREFIX + refreshToken;
        // Key: "blacklist:토큰값", Value: "logout", 만료 시간 설정
        redisTemplate.opsForValue().set(
                key,
                "logout",
                expirationTimeMillis,
                TimeUnit.MILLISECONDS
        );
    }

    public boolean isBlacklisted(String refreshToken) {
        String key = BLACKLIST_PREFIX + refreshToken;
        // Redis에서 해당 키로 저장된 값이 있는지 확인
        return redisTemplate.hasKey(key);
    }
}