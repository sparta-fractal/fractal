package com.sparta.team5.fractal.domain.product.service;

import com.sparta.team5.fractal.common.cache.CacheUtil;
import com.sparta.team5.fractal.domain.product.dto.ProductListResponse;
import com.sparta.team5.fractal.domain.search.service.SearchServiceApi;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductCacheService {

    private final CacheUtil cacheUtil;
    private final SearchServiceApi searchServiceApi;
    private final ProductServiceV2 productServiceV2;

    /**
     * products 캐시 초기화 후 데이터 집어넣는 메서드
     */
    public void refreshTopKeywordProductCache() {

        Cache cache = cacheUtil.getRequiredCache("products");

        cache.clear();

        List<String> keywords = searchServiceApi.getTopTenKeywords();

        Pageable pageable = PageRequest.of(0, 20, Sort.by(Sort.Direction.DESC, "createdAt"));

        keywords.forEach(keyword -> {
            ProductListResponse products = productServiceV2.getProductsByKeywordV2(pageable, keyword);

            cache.put(keyword, products);

            log.info("Cache Evicted products for: {}", keyword);
        });
    }
}
