package com.sparta.team5.fractal.common.aop;

import com.sparta.team5.fractal.common.exception.CommonErrorCode;
import com.sparta.team5.fractal.common.exception.GlobalException;
import com.sparta.team5.fractal.domain.product.dto.ProductListResponse;
import com.sparta.team5.fractal.domain.product.service.ProductServiceApi;
import com.sparta.team5.fractal.domain.search.service.SearchServiceApi;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Qualifier;
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
public class CacheEvictAspect {

    private final ProductServiceApi productServiceApi;
    private final SearchServiceApi searchServiceApi;
    private final CacheManager cacheManager;

    public CacheEvictAspect(@Qualifier("productServiceV2") ProductServiceApi productServiceApi,
                            SearchServiceApi searchServiceApi,
                            CacheManager cacheManager
    ) {
        this.cacheManager = cacheManager;
        this.productServiceApi = productServiceApi;
        this.searchServiceApi = searchServiceApi;
    }

    @After("(execution(* com.sparta.team5.fractal.domain.product.service.ProductServiceV2.createProduct(..)) ||" +
            "execution(* com.sparta.team5.fractal.domain.product.service.ProductServiceV2.updateProduct(..)) ||" +
            "execution(* com.sparta.team5.fractal.domain.product.service.ProductServiceV2.deleteProduct(..)))")
    public void productCacheEvict() {

        Cache cache = cacheManager.getCache("products");
        if (cache == null) {
            throw new GlobalException(CommonErrorCode.CACHE_IS_NULL);
        }
        cache.clear();

        List<String> keywords = searchServiceApi.getTopTenKeywords();
        // 이 부분 애매함
        Pageable pageable = PageRequest.of(0, 20, Sort.by(Sort.Direction.DESC, "createdAt"));

        keywords.forEach(keyword -> {
            // 키워드로 조회 후
            ProductListResponse products = productServiceApi.getProducts(pageable, keyword);

            // 캐시에 저장
            cache.put(keyword, products);

            log.info("Cache Evicted products for: {}", keyword);
        });
    }
}

