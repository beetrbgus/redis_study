package com.beetrb.redis_study.product.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Products, Long> {

    @Query(
        value =
            "select " +
                "max(products.id)" +
            "from Products products"
    )
    Integer findMaxProductsId();
}
