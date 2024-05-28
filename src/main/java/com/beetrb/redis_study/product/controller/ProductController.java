package com.beetrb.redis_study.product.controller;

import com.beetrb.redis_study.product.dto.ProductCreateReqDTO;
import com.beetrb.redis_study.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
