package com.beetrb.redis_study.product.service;

import com.beetrb.redis_study.product.domain.Products;
import com.beetrb.redis_study.product.domain.ProductRepository;
import com.beetrb.redis_study.product.dto.ProductCreateReqDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Override
    @Transactional
    public void createProduct(ProductCreateReqDTO createReqDTO) {
        Products products = Products.create(createReqDTO);
        productRepository.save(products);
    }

    @Override
    public Integer getLastIndex() {
        return productRepository.findMaxProductsId();
    }
}
