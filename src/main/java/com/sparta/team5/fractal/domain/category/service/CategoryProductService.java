package com.sparta.team5.fractal.domain.category.service;

import com.sparta.team5.fractal.common.core.exception.GlobalException;
import com.sparta.team5.fractal.domain.category.dto.CategoryProductResponse;
import com.sparta.team5.fractal.domain.category.entity.Category;
import com.sparta.team5.fractal.domain.category.exception.CategoryErrorCode;
import com.sparta.team5.fractal.domain.product.dto.ProductSimpleResponse;
import com.sparta.team5.fractal.domain.product.entity.Product;
import com.sparta.team5.fractal.domain.product.service.ProductServiceApi;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CategoryProductService {

    private final ProductServiceApi productServiceApi;
    private final CategoryServiceApi categoryServiceApi;

    @Transactional(readOnly = true)
    public CategoryProductResponse getCategory(Long categoryId, Pageable pageable) {

        Category category = categoryServiceApi.findById(categoryId)
                .orElseThrow(() -> new GlobalException(CategoryErrorCode.CATEGORY_NOT_FOUND));

        Page<Product> productPage = productServiceApi.findProductsByCategoryId(categoryId, pageable);

        Page<ProductSimpleResponse> productDtoPage = productPage.map(ProductSimpleResponse::from);

        return CategoryProductResponse.from(category, productDtoPage);
    }
}
