package com.beetrb.redis_study.redis.service;

import com.beetrb.redis_study.product.domain.CategoryType;
import com.beetrb.redis_study.product.dto.response.FavoriteProductResDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ProductRedisRepository {
    private static final String PRODUCT_ID = "productId::";
    private final RedisTemplate<String, FavoriteProductResDTO> redisTemplate;

    public void saveFavorites(CategoryType category, FavoriteProductResDTO favoriteProduct) {
        redisTemplate.opsForValue().set(PRODUCT_ID + category.name(), favoriteProduct, Duration.ofMinutes(3));
    }

    public Optional<FavoriteProductResDTO> getFavoriteByCategory(CategoryType category) {
        FavoriteProductResDTO favoriteProductResDTO = redisTemplate.opsForValue().get(PRODUCT_ID + category.name());

        return Optional.ofNullable(favoriteProductResDTO);
    }
}
