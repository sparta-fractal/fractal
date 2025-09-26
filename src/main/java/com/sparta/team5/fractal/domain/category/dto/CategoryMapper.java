package com.sparta.team5.fractal.domain.category.dto;

import com.sparta.team5.fractal.domain.category.entity.Category;

/**
 * Category 엔티티를 DTO로 변환하는 매퍼 클래스
 */
public class CategoryMapper {

    /**
     * Category 엔티티를 CategoryResponse로 변환
     * 
     * @param category 변환할 Category 엔티티
     * @return CategoryResponse DTO
     */
    public static CategoryResponse toResponse(Category category) {
        if (category == null) {
            return null;
        }

        Long parentCategoryId = extractParentCategoryId(category);

        return new CategoryResponse(
            category.getId(),
            category.getName(),
            parentCategoryId,
            category.getCreatedAt(),
            category.getUpdatedAt()
        );
    }

    /**
     * Category의 부모 카테고리 ID를 안전하게 추출
     * 
     * @param category Category 엔티티
     * @return 부모 카테고리 ID (없으면 null)
     */
    private static Long extractParentCategoryId(Category category) {
        if (category.getParentCategory() == null) {
            return null;
        }
        return category.getParentCategory().getId();
    }
}
