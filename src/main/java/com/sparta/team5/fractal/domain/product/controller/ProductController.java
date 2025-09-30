package com.sparta.team5.fractal.domain.product.controller;

import com.sparta.team5.fractal.common.response.ApiResponse;
import com.sparta.team5.fractal.domain.product.dto.ProductCreateRequest;
import com.sparta.team5.fractal.domain.product.dto.ProductListResponse;
import com.sparta.team5.fractal.domain.product.dto.ProductResponse;
import com.sparta.team5.fractal.domain.product.dto.ProductUpdateRequest;
import com.sparta.team5.fractal.domain.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products/")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    /**
     * 새로운 상품을 생성하는 엔드포인트
     *
     * @param request 상품 생성 요청 정보
     * @return 생성된 상품 정보
     */
    @PostMapping("/v1/products")
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
    @GetMapping("/v1/products/{productId}")
    public ResponseEntity<ApiResponse<ProductResponse>> getProduct(@PathVariable Long productId) {
        ProductResponse productResponse = productService.getProduct(productId);

        return ApiResponse.success(productResponse, "상품 조회에 성공했습니다.");
    }

    /**
     * 상품 목록 페이징 조회 엔드포인트 v1
     *
     * @param pageable 페이징 정보 (page, size, sort)
     * @return 페이징된 상품 목록
     * + RequestParam String keyword 추가
     */
    @GetMapping("/v1/products")
    public ResponseEntity<ApiResponse<ProductListResponse>> getProducts(
            @RequestParam(required = false) String keyword,
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {

        ProductListResponse productListResponse = productService.getProducts(pageable, keyword);

        return ApiResponse.success(productListResponse, "상품 목록 조회에 성공했습니다.");
    }

    /**
     * 상품 목록 페이징 조회 엔드포인트 v2
     *
     * @param pageable 페이징 정보 (page, size, sort)
     * @return 페이징된 상품 목록
     * + RequestParam String keyword 추가
     */
    @GetMapping("/v2/products")
    public ResponseEntity<ApiResponse<ProductListResponse>> getProductsV2(
            @RequestParam(required = false) String keyword,
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {

        ProductListResponse productListResponse = productService.getProductsV2(pageable, keyword);

        return ApiResponse.success(productListResponse, "상품 목록 조회에 성공했습니다.");
    }

    /**
     * 상품 수정 엔드포인트
     *
     * @param productId 수정할 상품 ID
     * @param request   상품 수정 요청 정보
     * @return 수정된 상품 정보
     */
    @PutMapping("/v1/products/{productId}")
    public ResponseEntity<ApiResponse<ProductResponse>> updateProduct(@PathVariable Long productId,
                                                                      @Valid @RequestBody ProductUpdateRequest request) {
        ProductResponse productResponse = productService.updateProduct(productId, request);

        return ApiResponse.success(productResponse, "상품이 성공적으로 수정되었습니다.");
    }

    /**
     * 상품 삭제 엔드포인트 (Soft Delete)
     *
     * @param productId 삭제할 상품 ID
     */
    @DeleteMapping("/v1/products/{productId}")
    public ResponseEntity<ApiResponse<Void>> deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
        return ApiResponse.success(null, "상품이 성공적으로 삭제되었습니다.");
    }
}
