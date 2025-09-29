package com.sparta.team5.fractal.domain.tag.dto.response;

import com.sparta.team5.fractal.domain.product.dto.ProductSimpleResponse;
import com.sparta.team5.fractal.domain.tag.entity.Tag;
import org.springframework.data.domain.Page;

public record TagProductResponse(
        Long id,
        String name,
        Long views,
        Page<ProductSimpleResponse> products
) {
    public static TagProductResponse from(Tag tag, Page<ProductSimpleResponse> productPage) {


        return new TagProductResponse(
                tag.getId(),
                tag.getName(),
                tag.getViews(),
                productPage
        );
    }
}
