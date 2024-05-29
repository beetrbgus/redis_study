package com.beetrb.redis_study.user.service;

import com.beetrb.redis_study.product.domain.CategoryType;
import com.beetrb.redis_study.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Component
@RequiredArgsConstructor
public class FavoriteCacheWarmUpManage {
    private final ProductService productService;
    @Scheduled(fixedDelay = 1000L * 60 * 60) // 60분
    public void warmupFavoriteProducts() {
        log.info("cache를 warmup 합니당-");
        Arrays
            .stream(CategoryType.values())
            .forEach(
                category -> productService.getfavoriteProductList(category)
            );
    }
}
