package com.sparta.team5.fractal.domain.category.service;

import com.sparta.team5.fractal.common.core.exception.GlobalException;
import com.sparta.team5.fractal.domain.category.dto.CategoryProductResponse;
import com.sparta.team5.fractal.domain.category.entity.Category;
import com.sparta.team5.fractal.domain.category.exception.CategoryErrorCode;
import com.sparta.team5.fractal.domain.product.dto.ProductSimpleResponse;
import com.sparta.team5.fractal.domain.product.entity.Product;
import com.sparta.team5.fractal.domain.product.service.ProductServiceApi;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryProductServiceV2 {
    private final ProductServiceApi productServiceApi;
    private final CategoryServiceApi categoryServiceApi;

    @Transactional(readOnly = true)
    @Cacheable(
            value = "categoryProducts",
            key = "'category:' + #categoryId + ':page:' + #pageable.pageNumber + ':size:' + #pageable.pageSize + ':sort:' + #pageable.sort"
    )
    public CategoryProductResponse getCategory(Long categoryId, Pageable pageable) {
        log.debug("Cache miss for categoryId: {}, pageable: {}", categoryId, pageable);

        Category category = categoryServiceApi.findById(categoryId)
                .orElseThrow(() -> new GlobalException(CategoryErrorCode.CATEGORY_NOT_FOUND));

        Page<Product> productPage = productServiceApi.findProductsByCategoryId(categoryId, pageable);

        Page<ProductSimpleResponse> productDtoPage = productPage.map(ProductSimpleResponse::from);

        return CategoryProductResponse.from(category, productDtoPage);
    }
}
