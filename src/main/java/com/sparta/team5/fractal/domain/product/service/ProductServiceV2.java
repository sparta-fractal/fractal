package com.sparta.team5.fractal.domain.product.service;

import com.sparta.team5.fractal.common.exception.CommonErrorCode;
import com.sparta.team5.fractal.common.exception.GlobalException;
import com.sparta.team5.fractal.domain.category.entity.Category;
import com.sparta.team5.fractal.domain.category.exception.CategoryErrorCode;
import com.sparta.team5.fractal.domain.category.service.CategoryServiceApi;
import com.sparta.team5.fractal.domain.product.dto.ProductCreateRequest;
import com.sparta.team5.fractal.domain.product.dto.ProductListResponse;
import com.sparta.team5.fractal.domain.product.dto.ProductResponse;
import com.sparta.team5.fractal.domain.product.dto.ProductUpdateRequest;
import com.sparta.team5.fractal.domain.product.entity.Product;
import com.sparta.team5.fractal.domain.product.exception.ProductErrorCode;
import com.sparta.team5.fractal.domain.product.repository.ProductRepository;
import com.sparta.team5.fractal.domain.search.service.SearchServiceApi;
import com.sparta.team5.fractal.domain.tag.entity.Tag;
import com.sparta.team5.fractal.domain.tag.service.TagServiceApi;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service("productServiceV2")
@RequiredArgsConstructor
@Transactional
public class ProductServiceV2 {

    private final ProductRepository productRepository;
    private final SearchServiceApi searchServiceApi;
    private final CacheManager cacheManager;

    private final TagServiceApi tagServiceApi;
    private final CategoryServiceApi categoryServiceApi;

    @CacheEvict(cacheNames = {"categoryProducts"}, allEntries = true)
    public ProductResponse createProduct(ProductCreateRequest request) {
        Set<Category> categories = processCategories(request.categoryIds());

        Product product = Product.of(
                request.title(),
                request.price(),
                request.description()
        );

        product.replaceCategories(categories);

        Set<Tag> tags = processTags(request.tags());
        product.replaceTags(tags);

        Product savedProduct = productRepository.save(product);

        return ProductResponse.from(savedProduct);
    }

    private Set<Category> processCategories(List<Long> categoryIds) {
        if (categoryIds == null || categoryIds.isEmpty()) {
            return Collections.emptySet();
        }

        return categoryIds.stream()
                .map(categoryId -> categoryServiceApi.findById(categoryId)
                        .orElseThrow(() -> new GlobalException(CategoryErrorCode.CATEGORY_NOT_FOUND)))
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    private Set<Tag> processTags(List<String> tagNames) {
        if (tagNames == null || tagNames.isEmpty()) {
            return Collections.emptySet();
        }

        return tagNames.stream()
                .map(name -> name == null ? "" : name.trim())
                .filter(s -> !s.isEmpty())
                .map(name -> tagServiceApi.findByName(name)
                        .orElseGet(() -> tagServiceApi.createTag(name)))
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    @CacheEvict(cacheNames = {"categoryProducts"}, allEntries = true)
    public ProductResponse updateProduct(Long productId, ProductUpdateRequest request) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new GlobalException(ProductErrorCode.PRODUCT_NOT_FOUND));

        Set<Category> categories = processCategories(request.categoryIds());

        product.update(
                request.title(),
                request.price(),
                request.description()
        );

        product.replaceCategories(categories);

        Set<Tag> tags = processTags(request.tags());
        product.replaceTags(tags);

        Product updatedProduct = productRepository.save(product);

        return ProductResponse.from(updatedProduct);
    }

    @CacheEvict(cacheNames = {"categoryProducts"}, allEntries = true)
    public void deleteProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new GlobalException(ProductErrorCode.PRODUCT_NOT_FOUND));

        product.delete();

        productRepository.save(product);
    }

    // 제품 전체 조회와 검색 시 keyword에 맞춰 해당 제품 제목을 조회 v2
    @Transactional
    public ProductListResponse getProducts(Pageable pageable, String keyword) {

        Cache cache = cacheManager.getCache("products");
        if (cache == null) {
            throw new GlobalException(CommonErrorCode.CACHE_IS_NULL);
        }

        if (keyword != null) {
            if (!searchServiceApi.existAndIncrease(keyword)) {
                searchServiceApi.createSearch(keyword);
            }

            ProductListResponse productListResponse = cache.get(keyword, ProductListResponse.class);
            if (productListResponse != null) {

                return productListResponse;
            }
        }

        Page<Product> productPage = productRepository.findAllByKeyword(pageable, keyword);

        return ProductListResponse.from(productPage);
    }
}
