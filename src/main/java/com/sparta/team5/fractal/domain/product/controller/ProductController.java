package com.sparta.team5.fractal.domain.product.controller;

import com.sparta.team5.fractal.common.response.ApiResponse;
import com.sparta.team5.fractal.domain.product.dto.ProductCreateRequest;
import com.sparta.team5.fractal.domain.product.dto.ProductResponse;
import com.sparta.team5.fractal.domain.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    /**
     * 새로운 상품을 생성하는 엔드포인트
     * 
     * @param request 상품 생성 요청 정보
     * @return 생성된 상품 정보
     */
    @PostMapping
    public ResponseEntity<ApiResponse<ProductResponse>> createProduct(@Valid @RequestBody ProductCreateRequest request) {
        ProductResponse productResponse = productService.createProduct(request);

        return ApiResponse.created(productResponse, "상품이 성공적으로 생성되었습니다.");
    }

    /**
     * 상품 단건 조회 엔드포인트
     * 
     * @param productId 조회할 상품 ID
     * @return 조회된 상품 정보
     */
    @GetMapping("/{productId}")
    public ResponseEntity<ApiResponse<ProductResponse>> getProduct(@PathVariable Long productId) {
        ProductResponse productResponse = productService.getProduct(productId);

        return ApiResponse.success(productResponse, "상품 조회에 성공했습니다.");
    }
}
