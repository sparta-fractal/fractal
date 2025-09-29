package com.sparta.team5.fractal.domain.category.dto;

import com.sparta.team5.fractal.domain.category.entity.Category;
import com.sparta.team5.fractal.domain.product.dto.ProductSimpleResponse;
import java.time.LocalDateTime;
import org.springframework.data.domain.Page;

public record CategoryProductResponse(
        Long id,
        String name,
        Long parentCategoryId, // 부모 카테고리 ID
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        Page<ProductSimpleResponse> products
) {
    public static CategoryProductResponse from(Category category, Page<ProductSimpleResponse> productPage) {

        // 부모 카테고리가 없는 경우(최상위 카테고리)를 대비해 null 처리
        Long parentId = java.util.Optional.ofNullable(category.getParentCategory())
                .map(com.sparta.team5.fractal.domain.category.entity.Category::getId)
                .orElse(null);

        return new CategoryProductResponse(
                category.getId(),
                category.getName(),
                parentId,
                category.getCreatedAt(),
                category.getUpdatedAt(),
                productPage
        );
    }
}
