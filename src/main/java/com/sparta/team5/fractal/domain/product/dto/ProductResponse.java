package com.sparta.team5.fractal.domain.product.dto;

import com.sparta.team5.fractal.domain.product.entity.Product;

import java.time.LocalDateTime;

public record ProductResponse(
    Long id,
    String title,
    String price,
    String description,
    String tags,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {

    public static ProductResponse from(Product product) {
        return new ProductResponse(
            product.getId(),
            product.getTitle(),
            product.getPrice(),
            product.getDescription(),
            product.getTags(),
            product.getCreatedAt(),
            product.getUpdatedAt()
        );
    }
}
