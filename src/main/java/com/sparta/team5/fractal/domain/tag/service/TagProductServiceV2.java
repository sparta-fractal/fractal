package com.sparta.team5.fractal.domain.tag.service;

import com.sparta.team5.fractal.common.cache.CacheUtil;
import com.sparta.team5.fractal.common.exception.GlobalException;
import com.sparta.team5.fractal.domain.product.dto.ProductSimpleResponse;
import com.sparta.team5.fractal.domain.tag.dto.response.TagProductResponse;
import com.sparta.team5.fractal.domain.tag.entity.Tag;
import com.sparta.team5.fractal.domain.tag.exception.TagErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class TagProductServiceV2 {

    private final CacheUtil cacheUtil;
    private final TagServiceApi tagServiceApi;
    private final TagCacheService tagCacheService;

    // 사용자별 태그 조회 기록을 관리하는 메모리 캐시
    // Key: userId, Value: 조회한 tagId들의 Set
    private final ConcurrentHashMap<Long, Set<Long>> userTagViewHistory = new ConcurrentHashMap<>();

    /**
     * 사용자별 태그 조회 (어뷰징 방지)
     * 동일한 사용자가 같은 태그를 여러 번 조회해도 조회수는 한 번만 증가한다
     *
     * @param tagId    조회할 태그 ID
     * @param userId   조회한 사용자 ID
     * @param pageable 페이징 정보
     * @return 태그 상세 정보와 관련 상품 목록
     */
    @Transactional(readOnly = true)
    public TagProductResponse getProductsByTagId(Long tagId, Long userId, Pageable pageable) {

        Tag tag = tagServiceApi.findById(tagId)
                .orElseThrow(() -> new GlobalException(TagErrorCode.TAG_NOT_FOUND));

        // 사용자별 조회 기록 확인 및 조회수 증가
        if (isFirstTimeViewing(tagId, userId)) {
            increaseCacheViewCount(tagId);
            recordUserView(tagId, userId);
        }

        Page<ProductSimpleResponse> productDtoPage = tagCacheService.findProductsByTagId(tagId, pageable);

        TagProductResponse response = TagProductResponse.from(tag, getCacheViewCount(tagId), productDtoPage);

        return response;
    }

    // 헬퍼 메서드
    // 캐시의 조회수 증가 메서드
    private void increaseCacheViewCount(Long tagId) {

        // 캐시의 view count를 업데이트 하는 코드
        cacheUtil.put("tagViewCountCache", tagId, getCacheViewCount(tagId) + 1);

        // DB view count를 업데이트 하는 코드
        // tagRepository.increateViewCount(tagId);
    }

    /**
     * 사용자가 특정 태그를 처음 조회하는지 확인
     *
     * @param tagId  태그 ID
     * @param userId 사용자 ID
     * @return 처음 조회하는 경우 true, 이미 조회한 경우 false
     */
    private boolean isFirstTimeViewing(Long tagId, Long userId) {

        Set<Long> userViewedTags = userTagViewHistory.get(userId);

        boolean response = userViewedTags == null || !userViewedTags.contains(tagId);

        return response;
    }

    /**
     * 사용자의 태그 조회 기록을 기록
     *
     * @param tagId  태그 ID
     * @param userId 사용자 ID
     */
    private void recordUserView(Long tagId, Long userId) {

        userTagViewHistory.computeIfAbsent(userId, k -> ConcurrentHashMap.newKeySet())
                .add(tagId);
    }

    // 캐시의 조회수 조회 메서드
    private Integer getCacheViewCount(Long tagId) {

        Integer originViewCount = cacheUtil.get("tagViewCountCache", tagId, Integer.class);

        Integer response = Objects.requireNonNullElse(originViewCount, 0);

        return response;    // 해당하는 키에 해당하는 값을 꺼내고, Integer 타입으로 함
    }

    // 캐시 초기화 메서드 (조회수 초기화) - 시간에 따라 자동 호출
    @Scheduled(cron = "0 0 0 * * *")    // 초 분 시 일 월 요일
    public void clearCacheViewCount() {

        cacheUtil.clearCache("tagViewCountCache");
    }
}
