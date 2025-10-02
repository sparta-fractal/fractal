package com.sparta.team5.fractal.common.crosscutting.aop;

import com.sparta.team5.fractal.domain.product.service.ProductCacheService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class CacheEvictAspect {

    private final ProductCacheService productCacheService;

    @After("(execution(* com.sparta.team5.fractal.domain.product.service.ProductServiceV1.createProduct(..)) ||" +
            "execution(* com.sparta.team5.fractal.domain.product.service.ProductServiceV1.updateProduct(..)) ||" +
            "execution(* com.sparta.team5.fractal.domain.product.service.ProductServiceV1.deleteProduct(..)))")
    public void productCacheEvict() {

        productCacheService.refreshTopKeywordProductCache();
    }
}

