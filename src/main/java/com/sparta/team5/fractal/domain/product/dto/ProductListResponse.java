package com.sparta.team5.fractal.domain.product.dto;

import com.sparta.team5.fractal.domain.product.entity.Product;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;

public record ProductListResponse(
    List<ProductSummary> products,
    int currentPage,
    int totalPages,
    long totalElements,
    boolean hasNext,
    boolean hasPrevious
) {

    public static ProductListResponse from(Page<Product> productPage) {
        List<ProductSummary> productSummaries = productPage.getContent()
            .stream()
            .map(ProductSummary::from)
            .toList();

        return new ProductListResponse(
            productSummaries,
            productPage.getNumber() + 1,  // 0-based를 1-based로 변환
            productPage.getTotalPages(),
            productPage.getTotalElements(),
            productPage.hasNext(),
            productPage.hasPrevious()
        );
    }

    public record ProductSummary(
        Long id,
        String title,
        String description,
        LocalDateTime createdAt
    ) {

        public static ProductSummary from(Product product) {
            return new ProductSummary(
                product.getId(),
                product.getTitle(),
                product.getDescription(),
                product.getCreatedAt()
            );
        }
    }
}
