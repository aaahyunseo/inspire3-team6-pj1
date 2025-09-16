package com.lgcns.inspire3_blog.user.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;

@Repository
@RequiredArgsConstructor
public class RefreshTokenRepository {

    private final RedisTemplate<String, Object> redisTemplate;

    public void save(String eamil, String refreshToken, long expirationMs) {
        redisTemplate.opsForValue().set(
                "RT:" + eamil,  // key 형식: RT:유저아이디
                refreshToken,
                Duration.ofMillis(expirationMs) // 만료시간 설정
        );
    }

    public String findByUserId(String userId) {
        Object token = redisTemplate.opsForValue().get("RT:" + userId);
        return token != null ? token.toString() : null;
    }

    public void delete(String email) {
        redisTemplate.delete("RT:" + email); 
    }
}
