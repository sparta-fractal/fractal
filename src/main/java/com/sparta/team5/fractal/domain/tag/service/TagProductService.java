package com.sparta.team5.fractal.domain.tag.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sparta.team5.fractal.common.exception.GlobalException;
import com.sparta.team5.fractal.domain.product.dto.ProductSimpleResponse;
import com.sparta.team5.fractal.domain.product.entity.Product;
import com.sparta.team5.fractal.domain.product.service.ProductServiceApi;
import com.sparta.team5.fractal.domain.tag.dto.response.TagProductResponse;
import com.sparta.team5.fractal.domain.tag.entity.Tag;
import com.sparta.team5.fractal.domain.tag.exception.TagErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TagProductService {

    private final ProductServiceApi productServiceApi;
    private final TagServiceApi tagServiceApi;

	@Transactional
	public TagProductResponse getTag(Long tagId, Pageable pageable) {
		Tag tag = tagServiceApi.findById(tagId)
			.orElseThrow(() -> new GlobalException(TagErrorCode.TAG_NOT_FOUND));

		tag.increaseViewCount();

		Page<Product> productPage = productServiceApi.findProductsByTagId(tagId, pageable);

		Page<ProductSimpleResponse> productDtoPage = productPage.map(ProductSimpleResponse::from);

		return TagProductResponse.from(tag, productDtoPage);
	}
}
