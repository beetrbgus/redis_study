package com.beetrb.redis_study.product.domain.repository;

import com.beetrb.redis_study.product.domain.CategoryType;
import com.beetrb.redis_study.product.dto.response.FavoriteProductDTO;

import java.util.List;

public interface ProductCustomRepository {
    List<FavoriteProductDTO> getfavoriteProductList(CategoryType categoryType);
}
