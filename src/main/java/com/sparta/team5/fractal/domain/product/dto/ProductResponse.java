package com.sparta.team5.fractal.domain.product.dto;

import com.sparta.team5.fractal.domain.product.entity.Product;
import com.sparta.team5.fractal.domain.product.entity.ProductTag;
import com.sparta.team5.fractal.domain.tag.entity.Tag;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record ProductResponse(
    Long id,
    String title,
    BigDecimal price,
    String description,
    List<String> tags,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {

    public static ProductResponse from(Product product) {
        List<String> tagNames = product.getProductTags().stream()
            .map(ProductTag::getTag)
            .map(Tag::getName)
            .toList();

        return new ProductResponse(
            product.getId(),
            product.getTitle(),
            product.getPrice(),
            product.getDescription(),
            tagNames,
            product.getCreatedAt(),
            product.getUpdatedAt()
        );
    }
}
