package com.sparta.team5.fractal.domain.category.controller;

import com.sparta.team5.fractal.common.core.dto.ApiResponse;
import com.sparta.team5.fractal.domain.category.dto.CategoryCreateRequest;
import com.sparta.team5.fractal.domain.category.dto.CategoryProductResponse;
import com.sparta.team5.fractal.domain.category.dto.CategoryResponse;
import com.sparta.team5.fractal.domain.category.service.CategoryProductService;
import com.sparta.team5.fractal.domain.category.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    private final CategoryProductService categoryProductService;

    /**
     * 모든 카테고리 조회
     *
     * @return 카테고리 목록
     */
    @GetMapping("/api/v1/categories")
    public ResponseEntity<ApiResponse<List<CategoryResponse>>> getAllCategories() {

        List<CategoryResponse> categories = categoryService.getAllCategories();

        return ApiResponse.success(categories, "카테고리 목록을 성공적으로 조회했습니다.");
    }

    @GetMapping("/api/v1/categories/{categoryId}")
    public ResponseEntity<ApiResponse<CategoryProductResponse>> getCategory(
            @PathVariable Long categoryId,
            @PageableDefault(size = 30, page = 0, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {

        CategoryProductResponse category = categoryProductService.getCategory(categoryId, pageable);

        return ApiResponse.success(category, "카테고리를 조회하였습니다.");
    }

    /**
     * 카테고리 생성
     *
     * @param request 카테고리 생성 요청 정보
     * @return 생성된 카테고리 정보
     */
    @PostMapping("/api/v1/categories")
    public ResponseEntity<ApiResponse<CategoryResponse>> createCategory(
            @Valid @RequestBody CategoryCreateRequest request) {

        CategoryResponse categoryResponse = categoryService.createCategory(request);

        return ApiResponse.success(categoryResponse, "카테고리가 성공적으로 생성되었습니다.");
    }

    /**
     * 카테고리 수정
     *
     * @param categoryId 수정할 카테고리 ID
     * @param request    카테고리 수정 요청 정보
     * @return 수정된 카테고리 정보
     */

    @PutMapping("/api/v1/categories/{categoryId}")
    public ResponseEntity<ApiResponse<CategoryResponse>> updateCategory(
            @PathVariable Long categoryId,
            @Valid @RequestBody CategoryCreateRequest request) {

        CategoryResponse categoryResponse = categoryService.updateCategory(categoryId, request);

        return ApiResponse.success(categoryResponse, "카테고리가 성공적으로 수정되었습니다.");
    }

    /**
     * 카테고리 삭제 (soft delete)
     *
     * @param categoryId 삭제할 카테고리 ID
     * @return 성공 응답
     */
    @DeleteMapping("/api/v1/categories/{categoryId}")
    public ResponseEntity<ApiResponse<Void>> deleteCategory(@PathVariable Long categoryId) {

        categoryService.deleteCategory(categoryId);

        return ApiResponse.success(null, "카테고리가 성공적으로 삭제되었습니다.");
    }
}
