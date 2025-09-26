package com.sparta.team5.fractal.domain.product.service;

import com.sparta.team5.fractal.domain.product.dto.ProductCreateRequest;
import com.sparta.team5.fractal.domain.product.dto.ProductResponse;
import com.sparta.team5.fractal.domain.product.entity.Product;
import com.sparta.team5.fractal.domain.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {

    private final ProductRepository productRepository;

    public ProductResponse createProduct(ProductCreateRequest request) {
        Product product = Product.of(
            request.title(),
            request.price(),
            request.description(),
            request.tags()
        );

        Product savedProduct = productRepository.save(product);

        return ProductResponse.from(savedProduct);
    }
}
