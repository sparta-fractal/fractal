package com.sparta.team5.fractal.domain.category.dto;

import com.sparta.team5.fractal.domain.category.entity.Category;
import com.sparta.team5.fractal.domain.product.dto.ProductSimpleResponse;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public record CategoryProductResponse(
        Long id,
        String name,
        Long parentCategoryId, // 부모 카테고리 ID
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        List<ProductSimpleResponse> products
) {
    public static CategoryProductResponse from(Category category) {
        // 카테고리에 속한 상품 목록을 DTO로 변환
        List<ProductSimpleResponse> productResponses = category.getProductCategories().stream()
                .map(productCategory -> ProductSimpleResponse.from(productCategory.getProduct()))
                .collect(Collectors.toList());

        // 부모 카테고리가 없는 경우(최상위 카테고리)를 대비해 null 처리
        Long parentId = (category.getParentCategory() != null) ? category.getParentCategory().getId() : null;

        return new CategoryProductResponse(
                category.getId(),
                category.getName(),
                parentId, // 누락되었던 parentCategoryId 추가
                category.getCreatedAt(), // 누락되었던 createdAt 추가
                category.getUpdatedAt(), // 누락되었던 updatedAt 추가
                productResponses
        );
    }
}
