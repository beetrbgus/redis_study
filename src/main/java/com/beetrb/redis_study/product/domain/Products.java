package com.beetrb.redis_study.product.domain;

import com.beetrb.redis_study.product.dto.ProductCreateReqDTO;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Getter
@Builder
@Entity
@Table(name = "tb_products")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Products {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private Integer price;
    private String manufacturer;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static Products create(ProductCreateReqDTO createReqDTO) {
        return Products.builder()
            .title(createReqDTO.getTitle())
            .manufacturer(createReqDTO.getManufaturer())
            .price(createReqDTO.getPrice())
            .build();
    }

    public Products(int randomNumer) {
        this.id = Long.valueOf(randomNumer);
    }
}
