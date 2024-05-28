package com.beetrb.redis_study.product.domain.repository;

import com.beetrb.redis_study.product.domain.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Products, Long>, ProductCustomRepository {

    @Query(
        value =
            "select " +
                "max(products.id)" +
            "from Products products"
    )
    Integer findMaxProductsId();
}
