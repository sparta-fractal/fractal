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
     * 특정 태그 ID로 상품 목록을 조회합니다.
     * 삭제되지 않은 상품만 조회됩니다.
     *
     * @param tagId    조회할 태그 ID
     * @param pageable 페이징 정보 (페이지 번호, 크기, 정렬)
     * @return 태그에 속한 상품 목록 (Page)
     */
    Page<Product> findProductsByTagId(@Param("tagId") Long tagId, Pageable pageable);

    /**
     * 특정 카테고리 ID로 상품 목록을 조회합니다.
     * 삭제되지 않은 상품만 조회됩니다.
     *
     * @param categoryId 조회할 카테고리 ID
     * @param pageable   페이징 정보 (페이지 번호, 크기, 정렬)
     * @return 카테고리에 속한 상품 목록 (Page)
     */
    Page<Product> findProductsByCategoryId(Long categoryId, Pageable pageable);
}