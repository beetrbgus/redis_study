package com.beetrb.redis_study.product.controller;

import com.beetrb.redis_study.product.domain.CategoryType;
import com.beetrb.redis_study.product.dto.ProductCreateReqDTO;
import com.beetrb.redis_study.product.dto.response.FavoriteProductResDTO;
import com.beetrb.redis_study.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RequestMapping("/api/products")
@RestController
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping
    public ResponseEntity createProduct(ProductCreateReqDTO createReqDTO) {
        productService.createProduct(createReqDTO);

        return ResponseEntity.ok()
                .build();
    }

    @GetMapping("/favorite/{genderType}")
    public ResponseEntity<FavoriteProductResDTO> getFavoriteProduct(@PathVariable("genderType") String genderType) {
        CategoryType type = Arrays.stream(CategoryType.values())
                                .filter(categoryType -> categoryType.name().equalsIgnoreCase(genderType))
                                .findFirst()
                                .orElse(CategoryType.ALL);

        FavoriteProductResDTO favoriteProductResDTO = productService.getfavoriteProductList(type);
        return ResponseEntity.ok(favoriteProductResDTO);
    }
}
