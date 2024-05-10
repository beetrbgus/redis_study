package com.beetrb.redis_study.redis.service;

import com.beetrb.redis_study.post.domain.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class PostReadThrough {
    private final RestTemplate restTemplate;

}