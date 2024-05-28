package com.beetrb.redis_study.product.service;

import com.beetrb.redis_study.product.dto.ProductCreateReqDTO;

public interface ProductService {
    void createProduct(ProductCreateReqDTO createReqDTO);
    Integer getLastIndex();
}
