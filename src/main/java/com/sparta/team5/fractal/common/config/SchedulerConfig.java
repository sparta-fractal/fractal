package com.sparta.team5.fractal.common.config;

import com.sparta.team5.fractal.common.exception.CommonErrorCode;
import com.sparta.team5.fractal.common.exception.GlobalException;
import com.sparta.team5.fractal.domain.product.dto.ProductListResponse;
import com.sparta.team5.fractal.domain.product.service.ProductServiceApi;
import com.sparta.team5.fractal.domain.search.service.SearchServiceApi;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class SchedulerConfig {

    private final ProductServiceApi productServiceApi;
    private final SearchServiceApi searchServiceApi;
    private final CacheManager cacheManager;

    // 밀리초 단위로 계산
    @Scheduled(fixedRate = 60000)
    @Transactional(readOnly = true)
    public void searchRankChange() {

        // 캐시 찾기
        Cache cache = cacheManager.getCache("products");
        if (cache == null) {
            throw new GlobalException(CommonErrorCode.CACHE_IS_NULL);
        }
        // 캐시 초기화
        cache.clear();

        List<String> keywords = searchServiceApi.getTopTenKeywords();
        // 이 부분 애매함
        Pageable pageable = PageRequest.of(0, 20, Sort.by(Sort.Direction.DESC, "createdAt"));

        keywords.forEach(keyword -> {
            // 키워드로 조회 후
            ProductListResponse products = productServiceApi.getProductsV2(pageable, keyword);

            // 캐시에 저장
            cache.put(keyword, products);

            log.info("Cache Evicted products for: {}", keyword);
        });
    }
}
