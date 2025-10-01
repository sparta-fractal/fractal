package com.sparta.team5.fractal.domain.product.service;

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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ProductService implements ProductServiceApi {

    private final SearchServiceApi searchServiceApi;
    private final TagServiceApi tagServiceApi;
    private final CategoryServiceApi categoryServiceApi;

    private final ProductRepository productRepository;

    public ProductResponse createProduct(ProductCreateRequest request) {

        Product product = Product.of(
                request.title(),
                request.price(),
                request.description()
        );

        Set<Category> categories = processCategories(request.categoryIds());
        product.replaceCategories(categories);

        Set<Tag> tags = processTags(request.tags());
        product.replaceTags(tags);

        Product savedProduct = productRepository.save(product);

        ProductResponse response = ProductResponse.from(savedProduct);

        return response;
    }

    @Transactional(readOnly = true)
    public ProductResponse getProduct(Long productId) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new GlobalException(ProductErrorCode.PRODUCT_NOT_FOUND));

        ProductResponse response = ProductResponse.from(product);

        return response;
    }

    // 제품 전체 조회와 검색 시 keyword에 맞춰 해당 제품 제목을 조회 v1
    @Transactional
    public ProductListResponse getProductsByKeyword(Pageable pageable, String keyword) {

        Page<Product> productPage = productRepository.findAllByKeyword(pageable, keyword);

        if (keyword != null && !searchServiceApi.existAndIncrease(keyword)) {
            searchServiceApi.createSearch(keyword);
        }

        ProductListResponse response = ProductListResponse.from(productPage);

        return response;
    }

    public ProductResponse updateProduct(Long productId, ProductUpdateRequest request) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new GlobalException(ProductErrorCode.PRODUCT_NOT_FOUND));

        product.update(
                request.title(),
                request.price(),
                request.description()
        );

        Set<Category> categories = processCategories(request.categoryIds());
        product.replaceCategories(categories);

        Set<Tag> tags = processTags(request.tags());
        product.replaceTags(tags);

        Product updatedProduct = productRepository.save(product);

        ProductResponse response = ProductResponse.from(updatedProduct);

        return response;
    }

    public void deleteProduct(Long productId) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new GlobalException(ProductErrorCode.PRODUCT_NOT_FOUND));

        product.delete();

        productRepository.save(product);
    }

    // 헬퍼 메서드
    private Set<Category> processCategories(List<Long> categoryIds) {

        if (categoryIds == null || categoryIds.isEmpty()) {
            return Collections.emptySet();
        }

        Set<Category> categories = categoryIds.stream()
                .map(categoryId -> categoryServiceApi.findById(categoryId)
                        .orElseThrow(() -> new GlobalException(CategoryErrorCode.CATEGORY_NOT_FOUND)))
                .collect(Collectors.toCollection(LinkedHashSet::new));

        return categories;
    }

    private Set<Tag> processTags(List<String> tagNames) {

        if (tagNames == null || tagNames.isEmpty()) {
            return Collections.emptySet();
        }

        Set<Tag> tags = tagNames.stream()
                .map(name -> name == null ? "" : name.trim())
                .filter(s -> !s.isEmpty())
                .map(name -> tagServiceApi.findByName(name)
                        .orElseGet(() -> tagServiceApi.createTag(name)))
                .collect(Collectors.toCollection(LinkedHashSet::new));

        return tags;
    }

    // ProductServiceApi 구현 메서드
    @Override
    @Transactional(readOnly = true)
    public Optional<Product> findById(Long productId) {

        Optional<Product> product = productRepository.findById(productId);

        return product;
    }

    // 타도메인에서 사용하는 메서드
    @Override
    public Page<Product> findProductsByTagId(Long tagId, Pageable pageable) {

        Page<Product> products = productRepository.findProductsByTagId(tagId, pageable);

        return products;
    }

    @Override
    public Page<Product> findProductsByCategoryId(Long categoryId, Pageable pageable) {

        Page<Product> products = productRepository.findProductsByCategoryId(categoryId, pageable);

        return products;
    }
}