package com.beetrb.redis_study.product.domain.repository;

import static com.beetrb.redis_study.product.domain.QProducts.products;
import static com.beetrb.redis_study.order.domain.QOrders.orders;

import com.beetrb.redis_study.product.domain.CategoryType;
import com.beetrb.redis_study.product.dto.response.FavoriteProductDTO;
import com.beetrb.redis_study.product.dto.response.QFavoriteProductDTO;
import com.beetrb.redis_study.user.domain.GenderType;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProductCustomRepositoryImpl implements ProductCustomRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<FavoriteProductDTO> getfavoriteProductList(CategoryType categoryType) {
        return queryFactory.select(
                new QFavoriteProductDTO(
                    products.id,
                    products.title,
                    products.price,
                    products.manufacturer,
                    orders.count().intValue()
                )
            )
            .from(orders)
            .innerJoin(products)
                .on(products.id.eq(orders.products.id))
            .fetchJoin()
            .where(
                searchCategory(categoryType)
            )
            .groupBy(products.id)
            .orderBy(products.id.count().desc())
            .limit(20)
            .fetch();
    }

    private BooleanExpression searchCategory(CategoryType categoryType) {
        if(CategoryType.MALE.equals(categoryType)) {
            return orders.user.gender.eq(GenderType.MALE);
        } else if(CategoryType.FEMALE.equals(categoryType)) {
            return orders.user.gender.eq(GenderType.FEMALE);
        }
        return null;
    }
}
