package com.sparta.team5.fractal.domain.product.controller;

import com.sparta.team5.fractal.common.response.ApiResponse;
import com.sparta.team5.fractal.domain.product.dto.ProductCreateRequest;
import com.sparta.team5.fractal.domain.product.dto.ProductListResponse;
import com.sparta.team5.fractal.domain.product.dto.ProductResponse;
import com.sparta.team5.fractal.domain.product.dto.ProductUpdateRequest;
import com.sparta.team5.fractal.domain.product.service.ProductServiceV2;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v2/products")
@RequiredArgsConstructor
public class ProductControllerV2 {

    private final ProductServiceV2 productServiceV2;

    /**
     * 새로운 상품을 생성하는 엔드포인트 v2
     *
     * @param request 상품 생성 요청 데이터 (이름, 설명, 카테고리, 태그 등)
     * @return 생성된 상품 정보와 성공 메시지
     */
    @PostMapping
    public ResponseEntity<ApiResponse<ProductResponse>> createProduct(@Valid @RequestBody ProductCreateRequest request) {

        ProductResponse productResponse = productServiceV2.createProduct(request);

        return ApiResponse.created(productResponse, "상품이 성공적으로 생성되었습니다.");
    }

    /**
     * 상품 목록 페이징 조회 엔드포인트 v2
     *
     * @param pageable 페이징 정보 (page, size, sort)
     * @return 페이징된 상품 목록 + RequestParam String keyword 추가
     */
    @GetMapping
    public ResponseEntity<ApiResponse<ProductListResponse>> getProductsV2(
            @RequestParam(required = false) String keyword,
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {

        ProductListResponse productListResponse = productServiceV2.getProductsByKeywordV2(pageable, keyword);

        return ApiResponse.success(productListResponse, "상품 목록 조회에 성공했습니다.");
    }

    /**
     * 기존 상품을 수정하는 엔드포인트 v2
     *
     * @param productId 수정할 상품의 ID
     * @param request   상품 수정 요청 데이터 (이름, 설명, 카테고리, 태그 등)
     * @return 수정된 상품 정보와 성공 메시지
     */
    @PutMapping("/{productId}")
    public ResponseEntity<ApiResponse<ProductResponse>> updateProduct(@PathVariable Long productId, @Valid @RequestBody ProductUpdateRequest request) {

        ProductResponse productResponse = productServiceV2.updateProduct(productId, request);

        return ApiResponse.success(productResponse, "상품이 성공적으로 수정되었습니다.");
    }

    /**
     * 기존 상품을 삭제하는 엔드포인트 v2
     *
     * @param productId 삭제할 상품의 ID
     * @return 성공 메시지 (응답 데이터는 없음)
     */
    @DeleteMapping("/{productId}")
    public ResponseEntity<ApiResponse<Void>> deleteProduct(@PathVariable Long productId) {

        productServiceV2.deleteProduct(productId);

        return ApiResponse.success(null, "상품이 성공적으로 삭제되었습니다.");
    }
}
