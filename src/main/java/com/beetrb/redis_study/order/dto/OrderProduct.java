package com.beetrb.redis_study.order.dto;

import com.beetrb.redis_study.product.domain.Products;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class OrderProduct {
    private Integer amount;
    private Products products;
}
