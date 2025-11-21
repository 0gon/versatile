package org.gon.comm;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.ZonedDateTime;
import java.util.Date;

@Slf4j
@Component
public class JwtUtil {

    private final SecretKey key;
    private final long accessTokenExpTime;
    public static final long REFRESH_TOKEN_EXP_TIME = 604800L; // 7일 (초 단위)

    private final String issuer = "gonpang-server-1";
    private final String audience = "gonpang-server";

    public JwtUtil(
            @Value("${jwt.secret}") String secretKey,
            @Value("${jwt.expiration_time}") long accessTokenExpTime
    ) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.accessTokenExpTime = accessTokenExpTime;
    }

    public String createAccessToken(Long subject) {
        return createToken(subject, accessTokenExpTime);
    }

    public String createRefreshToken(Long subject) {
        return createToken(subject, REFRESH_TOKEN_EXP_TIME);
    }


    /**
     * JWT 생성
     */
    private String createToken(Long subject, long expireTime) {

        ZonedDateTime now = ZonedDateTime.now();
        ZonedDateTime expireDateTime = now.plusSeconds(expireTime);


        return Jwts.builder()
                .claims()
                    .issuer(issuer) // 발급자
                    .subject(String.valueOf(subject)) // 식별자
                    .audience() // 대상
                        .add(audience).and()
                    .issuedAt(Date.from(now.toInstant()))
                    .expiration(Date.from(expireDateTime.toInstant())).and()
                .signWith(key)
                .compact();
    }


    /**
     * JWT 검증
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT Token", e);
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT Token", e);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT Token", e);
        } catch (IllegalArgumentException e) {
            log.info("JWT claims string is empty.", e);
        }
        return false;
    }


    /**
     * JWT Claims 추출
     */
    public Claims getClaims(String token) {
        try {
            return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }


}
