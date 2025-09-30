package com.sparta.team5.fractal.domain.product.service;

import com.sparta.team5.fractal.common.exception.CommonErrorCode;
import com.sparta.team5.fractal.common.exception.GlobalException;
import com.sparta.team5.fractal.domain.product.dto.ProductListResponse;
import com.sparta.team5.fractal.domain.product.entity.Product;
import com.sparta.team5.fractal.domain.product.repository.ProductRepository;
import com.sparta.team5.fractal.domain.search.service.SearchServiceApi;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service("productServiceV2")
@RequiredArgsConstructor
public class ProductServiceV2 {

    private final ProductRepository productRepository;
    private final SearchServiceApi searchServiceApi;
    private final CacheManager cacheManager;

    // 제품 전체 조회와 검색 시 keyword에 맞춰 해당 제품 제목을 조회 v2
    @Transactional
    public ProductListResponse getProducts(Pageable pageable, String keyword) {

        Cache cache = cacheManager.getCache("products");
        if (cache == null) {
            throw new GlobalException(CommonErrorCode.CACHE_IS_NULL);
        }

        if (keyword != null) {
            if (!searchServiceApi.existAndIncrease(keyword)) {
                searchServiceApi.createSearch(keyword);
            }

            ProductListResponse productListResponse = cache.get(keyword, ProductListResponse.class);
            if (productListResponse != null) {
                
                return productListResponse;
            }
        }

        Page<Product> productPage = productRepository.findAllByKeyword(pageable, keyword);

        return ProductListResponse.from(productPage);
    }
}
