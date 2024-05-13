package com.beetrb.redis_study.redis.service;

import com.beetrb.redis_study.post.domain.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PostRedisRepository {
    private static final String POST_KEY = "postId::";
    private final RedisTemplate<String, Object> redisTemplate;

    public void savePost(Post post) {
        redisTemplate.opsForValue().set(POST_KEY + post.getId(), post, Duration.ofMinutes(3));
    }

    public Optional<Post> getPost(Long postId) {
        Post post = (Post) redisTemplate.opsForValue().get(POST_KEY + postId);
        return Optional.ofNullable(post);
    }
}
