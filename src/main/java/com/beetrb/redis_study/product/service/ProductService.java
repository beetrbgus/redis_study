package com.beetrb.redis_study.product.service;

import com.beetrb.redis_study.product.domain.CategoryType;
import com.beetrb.redis_study.product.dto.ProductCreateReqDTO;
import com.beetrb.redis_study.product.dto.response.FavoriteProductResDTO;

public interface ProductService {
    void createProduct(ProductCreateReqDTO createReqDTO);
    FavoriteProductResDTO getfavoriteProductList(CategoryType categoryType);
}
