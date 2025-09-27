package com.sparta.team5.fractal.domain.tag.dto.response;

import com.sparta.team5.fractal.domain.product.dto.ProductSimpleResponse;
import com.sparta.team5.fractal.domain.tag.entity.Tag;
import java.util.List;
import java.util.stream.Collectors;

public record TagProductResponse(
        Long id,
        String name,
        Long views,
        List<ProductSimpleResponse> product
) {
    public static TagProductResponse from(Tag tag) {
        List<ProductSimpleResponse> productResponses = tag.getProductTags().stream()
                .map(productTag -> ProductSimpleResponse.from(productTag.getProduct()))
                .collect(Collectors.toList());

        return new TagProductResponse(
                tag.getId(),
                tag.getName(),
                tag.getViews(),
                productResponses
        );
    }
}
