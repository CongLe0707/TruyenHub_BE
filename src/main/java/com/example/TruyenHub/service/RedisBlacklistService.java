package com.example.TruyenHub.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

import static com.example.TruyenHub.utils.Constants.REDIS_BLACKLIST_PREFIX;

@Service
@RequiredArgsConstructor
public class RedisBlacklistService {

    private final StringRedisTemplate redisTemplate;

    public void blacklistToken(String token, long ttlSeconds) {
        if (token == null) return;
        redisTemplate.opsForValue().set(REDIS_BLACKLIST_PREFIX + token, "1", Duration.ofSeconds(ttlSeconds));
    }

    public boolean isBlacklisted(String token) {
        if (token == null) return false;
        Boolean has = redisTemplate.hasKey(REDIS_BLACKLIST_PREFIX + token);
        return has != null && has;
    }
}


