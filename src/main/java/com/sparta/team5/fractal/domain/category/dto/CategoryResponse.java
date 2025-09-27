package com.sparta.team5.fractal.domain.category.dto;

import com.sparta.team5.fractal.domain.category.entity.Category;
import java.time.LocalDateTime;

/**
 * 카테고리 응답 DTO
 *
 * @param id               카테고리 ID
 * @param name             카테고리명
 * @param parentCategoryId 부모 카테고리 ID
 * @param createdAt        생성일시
 * @param updatedAt        수정일시
 */
public record CategoryResponse(
        Long id,
        String name,
        Long parentCategoryId,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    /**
     * Category 엔티티를 CategoryResponse로 변환
     *
     * @param category 변환할 Category 엔티티
     * @return CategoryResponse DTO
     */
    public static CategoryResponse from(Category category) {

        return CategoryMapper.from(category);
    }
}