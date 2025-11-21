package org.gon.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    // ... Redis 연결 정보 설정 (host, port 등)
    private String host = "localhost"; // Redis 서버 호스트
    private int port = 6379; // Redis 서버 포트

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        // LettuceConnectionFactory는 기본 Spring Boot 설정 파일 (application.yml/properties)을 읽습니다.
        return new LettuceConnectionFactory(host, port);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory());

        // Key 직렬화 설정 (일반적으로 String)
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        // Value 직렬화 설정 (Refresh Token 문자열 저장 시 StringRedisSerializer 사용)
        redisTemplate.setValueSerializer(new StringRedisSerializer());

        // Hash Key/Value 직렬화 설정 (필요에 따라)
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new StringRedisSerializer());

        return redisTemplate;
    }
}