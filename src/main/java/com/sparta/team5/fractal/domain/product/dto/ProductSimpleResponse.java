package com.sparta.team5.fractal.domain.product.dto;

import com.sparta.team5.fractal.domain.product.entity.Product;
import java.math.BigDecimal;

public record ProductSimpleResponse(
        Long id,
        String title,
        BigDecimal price,
        String description
) {
    public static ProductSimpleResponse from(Product product) {
        return new ProductSimpleResponse(
                product.getId(),
                product.getTitle(),
                product.getPrice(),
                product.getDescription()
        );
    }
}


