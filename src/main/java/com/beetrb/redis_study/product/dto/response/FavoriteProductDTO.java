package com.beetrb.redis_study.product.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FavoriteProductDTO {
    private Long id; // 상품 id
    private String title;
    private Integer price;
    private String manufacturer;

    private Integer orderCount;

    @QueryProjection
    public FavoriteProductDTO(Long id, String title, Integer price, String manufacturer, Integer orderCount) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.manufacturer = manufacturer;
        this.orderCount = orderCount;
    }
}
