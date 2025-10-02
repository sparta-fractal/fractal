package com.sparta.team5.fractal.common.infra.cache;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public CacheManager cacheManager() {
        // 아래와 같이 자신이 사용할 캐시 추가, 설정
        CaffeineCache products = new CaffeineCache("products",
                Caffeine.newBuilder()
                        .maximumSize(10_000)
                        .build());

        CaffeineCache tagViewCountCache = new CaffeineCache("tagViewCountCache",
                Caffeine.newBuilder()
                        .expireAfterWrite(Duration.ofHours(24))
                        .maximumSize(2000) // 캐시 최대 크기
                        .build());

        CaffeineCache productListCache = new CaffeineCache("productListCache",
                Caffeine.newBuilder()
                        .expireAfterWrite(Duration.ofHours(24))
                        .maximumSize(2000) // 캐시 최대 크기
                        .build());

        CaffeineCache categoryProducts = new CaffeineCache("categoryProducts",
                Caffeine.newBuilder()
                        // 10분 동안 쓰기가 없으면 만료
                        .expireAfterWrite(10, TimeUnit.MINUTES)
                        // 최대 1000개의 항목까지 저장
                        .maximumSize(1000)
                        .build());

        SimpleCacheManager cacheManager = new SimpleCacheManager();

        // 아래 해당 리스트 안에 추가한 캐시 리스트에 넣어주면 정상작동 가능
        cacheManager.setCaches(List.of(products, categoryProducts, tagViewCountCache, productListCache));
        return cacheManager;
    }
}
