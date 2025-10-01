package com.sparta.team5.fractal.common.config;

import java.time.Duration;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.benmanes.caffeine.cache.Caffeine;

@Configuration
@EnableCaching    // 캐시 기능 활성화 (@Cacheable, @CachePut, @CacheEvict 등 사용 가능)
public class CacheConfig {    // CacheManager를 등록

	@Bean
	public CacheManager cacheManager() {
		CaffeineCacheManager cacheManager = new CaffeineCacheManager("tagViewCountCache", "productListCache",
			"categoriesCache");

		cacheManager.setCaffeine(Caffeine.newBuilder()
			.expireAfterWrite(Duration.ofHours(24))
			// .expireAfterWrite(10, TimeUnit.MINUTES)
			// .expireAfterWrite(Duration.ofMinutes(10))    // 만료 시간 설정
			.maximumSize(2000) // 캐시 최대 크기
		);

		return cacheManager;
	}
}
