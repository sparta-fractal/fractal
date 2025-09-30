package com.sparta.team5.fractal.common.aop;

import com.sparta.team5.fractal.common.exception.CommonErrorCode;
import com.sparta.team5.fractal.common.exception.GlobalException;
import com.sparta.team5.fractal.domain.product.dto.ProductListResponse;
import com.sparta.team5.fractal.domain.product.service.ProductServiceApi;
import com.sparta.team5.fractal.domain.search.service.SearchServiceApi;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class CacheEvictAspect {

    private final ProductServiceApi productServiceApi;
    private final SearchServiceApi searchServiceApi;
    private final CacheManager cacheManager;

    @After("(execution(* com.sparta.team5.fractal.domain.product.service.ProductService.createProduct(..)) ||" +
            "execution(* com.sparta.team5.fractal.domain.product.service.ProductService.updateProduct(..)) ||" +
            "execution(* com.sparta.team5.fractal.domain.product.service.ProductService.deleteProduct(..)))")
    public void productCacheEvict() {

        Cache cache = cacheManager.getCache("products");
        if (cache == null) {
            throw new GlobalException(CommonErrorCode.CACHE_IS_NULL);
        }
        cache.clear();

        List<String> keywords = searchServiceApi.getTopTenKeywords();
        
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

