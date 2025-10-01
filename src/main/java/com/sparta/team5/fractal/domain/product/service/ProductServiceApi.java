package com.sparta.team5.fractal.domain.product.service;

import com.sparta.team5.fractal.domain.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

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
     * 태그 ID로 상품을 조회합니다.
     * 페이징된 상품 목록을 조회합니다.
     * 삭제되지 않은 상품만 조회됩니다.
     *
     * @param pageable 페이징 정보 (페이지 번호, 크기, 정렬)
     * @return 페이징된 상품 목록
     */
    Page<Product> findProductsByTagId(@Param("tagId") Long tagId, Pageable pageable);

    /**
     * 카테고리 ID로 상품을 조회합니다.
     * 페이징된 상품 목록을 조회합니다.
     * 삭제되지 않은 상품만 조회됩니다.
     *
     * @param pageable 페이징 정보 (페이지 번호, 크기, 정렬)
     * @return 페이징된 상품 목록
     */
    Page<Product> findProductsByCategoryId(@Param("categoryId") Long categoryId, Pageable pageable);
}