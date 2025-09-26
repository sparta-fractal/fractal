package com.sparta.team5.fractal.domain.product.dto;

import com.sparta.team5.fractal.domain.product.entity.Product;
import com.sparta.team5.fractal.domain.product.entity.ProductTag;
import com.sparta.team5.fractal.domain.product.entity.ProductCategory;
import com.sparta.team5.fractal.domain.tag.entity.Tag;
import com.sparta.team5.fractal.domain.category.entity.Category;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record ProductResponse(
    Long id,
    String title,
    BigDecimal price,
    String description,
    List<CategoryInfo> categories,
    List<String> tags,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {

    public static ProductResponse from(Product product) {
        List<String> tagNames = product.getProductTags().stream()
            .map(ProductTag::getTag)
            .map(Tag::getName)
            .toList();

        List<CategoryInfo> categoryInfos = product.getProductCategories().stream()
            .map(ProductCategory::getCategory)
            .map(CategoryInfo::from)
            .toList();

        return new ProductResponse(
            product.getId(),
            product.getTitle(),
            product.getPrice(),
            product.getDescription(),
            categoryInfos,
            tagNames,
            product.getCreatedAt(),
            product.getUpdatedAt()
        );
    }

    public record CategoryInfo(
        Long id,
        String name
    ) {
        public static CategoryInfo from(Category category) {
            return new CategoryInfo(category.getId(), category.getName());
        }
    }
}
