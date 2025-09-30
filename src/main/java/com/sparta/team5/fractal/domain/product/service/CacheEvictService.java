package com.sparta.team5.fractal.domain.product.service;

import com.sparta.team5.fractal.common.exception.CommonErrorCode;
import com.sparta.team5.fractal.common.exception.GlobalException;
import com.sparta.team5.fractal.domain.product.dto.ProductListResponse;
import com.sparta.team5.fractal.domain.search.service.SearchServiceApi;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CacheEvictService {

    private final CacheManager cacheManager;
    private final SearchServiceApi searchServiceApi;
    private final ProductServiceV2 productServiceV2;

    /**
     * products 캐시 초기화 후 데이터 집어넣는 메서드
     */
    public void productCacheEvict() {

        // 캐시 찾기
        Cache cache = cacheManager.getCache("products");
        if (cache == null) {
            throw new GlobalException(CommonErrorCode.CACHE_IS_NULL);
        }
        // 캐시 초기화
        cache.clear();

        List<String> keywords = searchServiceApi.getTopTenKeywords();

        Pageable pageable = PageRequest.of(0, 20, Sort.by(Sort.Direction.DESC, "createdAt"));

        keywords.forEach(keyword -> {
            // 키워드로 조회 후
            ProductListResponse products = productServiceV2.getProducts(pageable, keyword);

            // 캐시에 저장
            cache.put(keyword, products);

            log.info("Cache Evicted products for: {}", keyword);
        });
    }
}
