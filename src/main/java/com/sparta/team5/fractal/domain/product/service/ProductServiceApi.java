package com.sparta.team5.fractal.domain.product.service;

import com.sparta.team5.fractal.domain.product.dto.ProductListResponse;
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
     * 상품이 존재하는지 확인합니다.
     * 삭제되지 않은 상품만 확인됩니다.
     *
     * @param productId 확인할 상품 ID
     * @return 상품 존재 여부
     */
    boolean existsById(Long productId);

    /**
     * 페이징된 상품 목록을 조회합니다.
     * 삭제되지 않은 상품만 조회됩니다.
     *
     * @param pageable 페이징 정보 (페이지 번호, 크기, 정렬)
     * @return 페이징된 상품 목록
     */
    Page<Product> findAll(Pageable pageable);


    Page<Product> findProductsByTagId(@Param("tagId") Long tagId, Pageable pageable);

    Page<Product> findProductsByCategoryId(Long categoryId, Pageable pageable);

    /**
     * 페이징된 전체 상품 목록을 조회합니다.
     * 삭제되지 않은 상품들만 조회됩니다.
     *
     * @param pageable 페이징 정보 (페이지 번호, 크기, 정렬)
     * @param keyword  검색 키워드
     * @return 페이징된 전체 상품 목록
     */
    ProductListResponse getProductsV2(Pageable pageable, String keyword);

    /**
     * 캐시를 초기화합니다.
     * 검색어 조회가 많은 순으로 10개의 데이터를 캐시에 저장합니다.
     */
    void productCacheEvict();
}