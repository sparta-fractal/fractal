package com.sparta.team5.fractal.domain.product.service;

import com.sparta.team5.fractal.domain.product.entity.Product;

import java.util.Optional;

public interface ProductServiceApi {

    /**
     * 상품 ID로 상품을 조회합니다.
     * 삭제되지 않은 상품만 조회됩니다.
     * 
     * @param productId 조회할 상품 ID
     * @return 조회된 상품 (Optional)
     */
    Optional<Product> findById(Long productId);

    /**
     * 상품이 존재하는지 확인합니다.
     * 삭제되지 않은 상품만 확인됩니다.
     * 
     * @param productId 확인할 상품 ID
     * @return 상품 존재 여부
     */
    boolean existsById(Long productId);
}