package com.beetrb.redis_study.redis.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class PostRedisRepository {
    private static final String POST_KEY = "postId::";
    private final RedisTemplate<String, String> redisTemplate;

    public void setValues(String postId, String viewCount) {
        redisTemplate.opsForValue().set(POST_KEY + postId, viewCount, Duration.ofMinutes(3));
    }

    public String getPostViewCount(String postId) {
        return redisTemplate.opsForValue().get(POST_KEY + postId);
    }
}
