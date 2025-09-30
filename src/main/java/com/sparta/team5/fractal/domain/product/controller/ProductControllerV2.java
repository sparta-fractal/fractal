package com.sparta.team5.fractal.domain.product.controller;

import com.sparta.team5.fractal.common.response.ApiResponse;
import com.sparta.team5.fractal.domain.product.dto.ProductListResponse;
import com.sparta.team5.fractal.domain.product.service.ProductServiceV2;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v2/products")
@RequiredArgsConstructor
public class ProductControllerV2 {

    private final ProductServiceV2 productServiceV2;

    /**
     * 상품 목록 페이징 조회 엔드포인트 v2
     *
     * @param pageable 페이징 정보 (page, size, sort)
     * @return 페이징된 상품 목록
     * + RequestParam String keyword 추가
     */
    @GetMapping
    public ResponseEntity<ApiResponse<ProductListResponse>> getProductsV2(
            @RequestParam(required = false) String keyword,
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {

        ProductListResponse productListResponse = productServiceV2.getProducts(pageable, keyword);

        return ApiResponse.success(productListResponse, "상품 목록 조회에 성공했습니다.");
    }
}
