package com.sparta.team5.fractal.domain.category.controller;

import com.sparta.team5.fractal.common.core.dto.ApiResponse;
import com.sparta.team5.fractal.domain.category.dto.CategoryCreateRequest;
import com.sparta.team5.fractal.domain.category.dto.CategoryProductResponse;
import com.sparta.team5.fractal.domain.category.dto.CategoryResponse;
import com.sparta.team5.fractal.domain.category.service.CategoryProductServiceV2;
import com.sparta.team5.fractal.domain.category.service.CategoryServiceV2;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/categories")
public class CategoryControllerV2 {
    private final CategoryServiceV2 categoryServiceV2;
    private final CategoryProductServiceV2 categoryProductServiceV2;


    @GetMapping("/{categoryId}")
    public ResponseEntity<ApiResponse<CategoryProductResponse>> getCategory(
            @PathVariable Long categoryId,
            @PageableDefault(size = 30, page = 0, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {

        CategoryProductResponse category = categoryProductServiceV2.getCategory(categoryId, pageable);

        return ApiResponse.success(category, "카테고리를 조회하였습니다.");
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CategoryResponse>> createCategory(
            @Valid @RequestBody CategoryCreateRequest request) {

        CategoryResponse categoryResponse = categoryServiceV2.createCategory(request);

        return ApiResponse.success(categoryResponse, "카테고리가 성공적으로 생성되었습니다.");
    }

    /**
     * 카테고리 수정
     *
     * @param categoryId 수정할 카테고리 ID
     * @param request    카테고리 수정 요청 정보
     * @return 수정된 카테고리 정보
     */

    @PutMapping("/{categoryId}")
    public ResponseEntity<ApiResponse<CategoryResponse>> updateCategory(
            @PathVariable Long categoryId,
            @Valid @RequestBody CategoryCreateRequest request) {

        CategoryResponse categoryResponse = categoryServiceV2.updateCategory(categoryId, request);

        return ApiResponse.success(categoryResponse, "카테고리가 성공적으로 수정되었습니다.");
    }

    /**
     * 카테고리 삭제 (soft delete)
     *
     * @param categoryId 삭제할 카테고리 ID
     * @return 성공 응답
     */
    @DeleteMapping("/api/v2/categories/{categoryId}")
    public ResponseEntity<ApiResponse<Void>> deleteCategory(@PathVariable Long categoryId) {

        categoryServiceV2.deleteCategory(categoryId);

        return ApiResponse.success(null, "카테고리가 성공적으로 삭제되었습니다.");
    }
}
