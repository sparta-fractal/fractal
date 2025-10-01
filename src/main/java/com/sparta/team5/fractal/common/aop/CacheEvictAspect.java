package com.sparta.team5.fractal.common.aop;

import com.sparta.team5.fractal.domain.product.service.CacheEvictService;
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

    private final CacheEvictService cacheEvictService;

    @After("(execution(* com.sparta.team5.fractal.domain.product.service.ProductService.createProduct(..)) ||" +
            "execution(* com.sparta.team5.fractal.domain.product.service.ProductService.updateProduct(..)) ||" +
            "execution(* com.sparta.team5.fractal.domain.product.service.ProductService.deleteProduct(..)))")
    public void productCacheEvict() {

        cacheEvictService.refreshTopKeywordProductCache();
    }
}

