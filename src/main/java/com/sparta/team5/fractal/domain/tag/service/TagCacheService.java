package com.sparta.team5.fractal.domain.tag.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.sparta.team5.fractal.domain.product.dto.ProductSimpleResponse;
import com.sparta.team5.fractal.domain.product.entity.Product;
import com.sparta.team5.fractal.domain.product.service.ProductServiceApi;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class TagCacheService {
	private final ProductServiceApi productServiceApi;

	// 로직 빼오기
	@Cacheable(key = "#tagId + '_' + #pageable.getPageSize() + '_' + #pageable.getPageNumber() + '_' + #pageable.getSort().toString()", value = "productListCache")
	public Page<ProductSimpleResponse> findProductsByTagId(Long tagId, Pageable pageable) {
		// log.debug(">>> 캐시 키 생성: {}_{}_{}_{}", tagId, pageable.getPageNumber(), pageable.getPageSize(),
		// 	pageable.getSort().toString());
		Page<Product> productPage = productServiceApi.findProductsByTagId(tagId, pageable);

		Page<ProductSimpleResponse> productDtoPage = productPage.map(ProductSimpleResponse::from);

		return productDtoPage;
	}
}
