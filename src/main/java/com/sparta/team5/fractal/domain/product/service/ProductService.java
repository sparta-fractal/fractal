package com.sparta.team5.fractal.domain.product.service;

import com.sparta.team5.fractal.domain.product.dto.ProductCreateRequest;
import com.sparta.team5.fractal.domain.product.dto.ProductListResponse;
import com.sparta.team5.fractal.domain.product.dto.ProductResponse;
import com.sparta.team5.fractal.domain.product.dto.ProductUpdateRequest;
import com.sparta.team5.fractal.domain.product.entity.Product;
import com.sparta.team5.fractal.domain.product.exception.ProductErrorCode;
import com.sparta.team5.fractal.domain.product.repository.ProductRepository;
import com.sparta.team5.fractal.domain.tag.entity.Tag;
import com.sparta.team5.fractal.domain.tag.repository.TagRepository;
import com.sparta.team5.fractal.common.exception.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService implements ProductServiceApi {

    private final ProductRepository productRepository;
    private final TagRepository tagRepository;

    public ProductResponse createProduct(ProductCreateRequest request) {
        Product product = Product.of(
            request.title(),
            request.price(),
            request.description()
        );

        Set<Tag> tags = request.tags().stream()
            .map(String::trim)
            .filter(s -> !s.isEmpty())
            .map(name -> tagRepository.findByName(name).orElseGet(() -> tagRepository.save(Tag.of(name))))
            .collect(Collectors.toSet());

        product.replaceTags(tags);

        Product savedProduct = productRepository.save(product);

        return ProductResponse.from(savedProduct);
    }

    @Transactional(readOnly = true)
    public ProductResponse getProduct(Long productId) {
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new GlobalException(ProductErrorCode.PRODUCT_NOT_FOUND));

        return ProductResponse.from(product);
    }

    // ProductServiceApi 구현 메서드들
    @Override
    @Transactional(readOnly = true)
    public Optional<Product> findById(Long productId) {
        return productRepository.findById(productId);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Long productId) {
        return productRepository.existsById(productId);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Product> findAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public ProductListResponse getProducts(Pageable pageable) {
        Page<Product> productPage = productRepository.findAll(pageable);
        
        return ProductListResponse.from(productPage);
    }

    public ProductResponse updateProduct(Long productId, ProductUpdateRequest request) {
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new GlobalException(ProductErrorCode.PRODUCT_NOT_FOUND));

        product.update(
            request.title(),
            request.price(),
            request.description()
        );

        Set<Tag> tags = request.tags().stream()
            .map(String::trim)
            .filter(s -> !s.isEmpty())
            .map(name -> tagRepository.findByName(name).orElseGet(() -> tagRepository.save(Tag.of(name))))
            .collect(Collectors.toSet());
        product.replaceTags(tags);

        return ProductResponse.from(product);
    }

    public void deleteProduct(Long productId) {
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new GlobalException(ProductErrorCode.PRODUCT_NOT_FOUND));

        product.delete();
        
        productRepository.save(product);
    }
}
