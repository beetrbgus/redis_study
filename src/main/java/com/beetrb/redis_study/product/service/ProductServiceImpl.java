package com.beetrb.redis_study.product.service;

import com.beetrb.redis_study.product.domain.CategoryType;
import com.beetrb.redis_study.product.domain.Products;
import com.beetrb.redis_study.product.domain.repository.ProductRepository;
import com.beetrb.redis_study.product.dto.ProductCreateReqDTO;
import com.beetrb.redis_study.product.dto.response.FavoriteProductDTO;
import com.beetrb.redis_study.product.dto.response.FavoriteProductResDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    public FavoriteProductResDTO getfavoriteProductList(CategoryType categoryType) {
        List<FavoriteProductDTO> favoriteProductDTOList = productRepository.getfavoriteProductList(categoryType);

        return new FavoriteProductResDTO(favoriteProductDTOList);
    }
}
