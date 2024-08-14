package com.example.agiletracker.agile_tracker.repository;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import java.util.concurrent.TimeUnit;

@Repository
public class RedisJwtTokenRepository {

    private final RedisTemplate<String, String> redisTemplate;
    private final String BLACKLIST_PREFIX = "blacklist:";
    private final long TOKEN_EXPIRATION_TIME = 3600; // 1 hour, adjust as needed

    public RedisJwtTokenRepository(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void addToBlacklist(String token) {
        String key = BLACKLIST_PREFIX + token;
        redisTemplate.opsForValue().set(key, "blacklisted", TOKEN_EXPIRATION_TIME, TimeUnit.SECONDS);
    }

    public boolean isTokenBlacklisted(String token) {
        String key = BLACKLIST_PREFIX + token;
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }
}
