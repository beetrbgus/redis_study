package com.beetrb.redis_study.product.service;

import com.beetrb.redis_study.product.domain.CategoryType;
import com.beetrb.redis_study.product.domain.Products;
import com.beetrb.redis_study.product.domain.repository.ProductRepository;
import com.beetrb.redis_study.product.dto.ProductCreateReqDTO;
import com.beetrb.redis_study.product.dto.response.FavoriteProductDTO;
import com.beetrb.redis_study.product.dto.response.FavoriteProductResDTO;
import com.beetrb.redis_study.redis.service.ProductRedisRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductRedisRepository productRedisRepository;
    @Override
    @Transactional
    public void createProduct(ProductCreateReqDTO createReqDTO) {
        Products products = Products.create(createReqDTO);
        productRepository.save(products);
    }

    @Override
    public FavoriteProductResDTO getfavoriteProductList(CategoryType category) {
        Optional<FavoriteProductResDTO> favoriteProductResDTO = productRedisRepository.getFavoriteByCategory(category);

        if(favoriteProductResDTO.isPresent()) {
            FavoriteProductResDTO productResDTO = favoriteProductResDTO.get();
            log.info("레디스에서 가져온 데이터");
            return productResDTO;
        } else {
            List<FavoriteProductDTO> favoriteProductDTOList = productRepository.getfavoriteProductList(category);
            FavoriteProductResDTO productResDTO = new FavoriteProductResDTO(favoriteProductDTOList);

            productRedisRepository.saveFavorites(category, productResDTO);
            log.info("DB에서 가져온 데이터 ");
            return productResDTO;
        }

    }
}
