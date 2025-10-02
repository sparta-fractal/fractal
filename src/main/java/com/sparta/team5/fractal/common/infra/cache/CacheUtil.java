package com.sparta.team5.fractal.common.infra.cache;

import com.sparta.team5.fractal.common.core.exception.CommonErrorCode;
import com.sparta.team5.fractal.common.core.exception.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CacheUtil {

    private final CacheManager cacheManager;

    /**
     * 캐시 이름으로 캐시 객체를 가져온다.
     * 캐시가 존재하지 않으면 GlobalException을 던진다.
     *
     * @param cacheName 캐시 이름
     * @return Cache 객체
     */
    public Cache getRequiredCache(String cacheName) {

        Cache cache = cacheManager.getCache(cacheName);

        if (cache == null) {
            throw new GlobalException(CommonErrorCode.CACHE_IS_NULL);
        }

        return cache;
    }

    /**
     * 캐시 초기화 (clear)
     */
    public void clearCache(String cacheName) {
        Cache cache = getRequiredCache(cacheName);
        cache.clear();
    }

    /**
     * 캐시 삭제
     */
    public void evict(String cacheName, Object key) {
        Cache cache = getRequiredCache(cacheName);
        cache.evict(key);
    }

    /**
     * 캐시 조회
     */
    public <T> T get(String cacheName, Object key, Class<T> type) {
        Cache cache = getRequiredCache(cacheName);
        return cache.get(key, type);
    }

    /**
     * 캐시 저장
     */
    public void put(String cacheName, Object key, Object value) {
        Cache cache = getRequiredCache(cacheName);
        cache.put(key, value);
    }
}