package com.sparta.team5.fractal.domain.tag.service;

import java.util.Objects;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sparta.team5.fractal.common.exception.GlobalException;
import com.sparta.team5.fractal.domain.product.dto.ProductSimpleResponse;
import com.sparta.team5.fractal.domain.tag.dto.response.TagProductResponse;
import com.sparta.team5.fractal.domain.tag.entity.Tag;
import com.sparta.team5.fractal.domain.tag.exception.TagErrorCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TagProductServiceV2 {

	private final TagServiceApi tagServiceApi;
	private final CacheManager cacheManager;
	private final TagCacheService tagCacheService;

	// 캐싱을 적용할 메서드
	@Transactional
	public TagProductResponse getTag(Long tagId, Pageable pageable) {

		Tag tag = findById(tagId);

		Page<ProductSimpleResponse> productDtoPage = tagCacheService.findProductsByTagId(tagId, pageable);

		return TagProductResponse.from(tag, getCacheViewCount(tagId), productDtoPage);
	}

	// 여기서 해당 태그의 조회수를 증가시킬 거임
	public Tag findById(Long tagId) {
		Tag tag = tagServiceApi.findById(tagId)
			.orElseThrow(() -> new GlobalException(TagErrorCode.TAG_NOT_FOUND));

		increaseCacheViewCount(tag.getId());

		return tag;
	}

	// 캐시의 조회수 증가 메서드
	public void increaseCacheViewCount(Long tagId) {

		// cacheManager 에서 이름이 tagViewCountCache인 캐시 객체 가져올게!
		Cache cache = cacheManager.getCache("tagViewCountCache");

		// tagViewCountCache라는 캐시 객체가 없다면
		if (cache == null) {
			throw new IllegalStateException("tagViewCountCache라는 이름의 캐시가 존재하지 않습니다.");
		}

		// 캐시의 view count를 업데이트 하는 코드
		Integer currentCacheViewCount = getCacheViewCount(tagId);

		cache.put(tagId, currentCacheViewCount + 1);

		// DB view count를 업데이트 하는 코드
		// tagRepository.increateViewCount(tagId);
	}

	// 캐시의 조회수 조회 메서드
	public Integer getCacheViewCount(Long tagId) {
		Cache cache = cacheManager.getCache("tagViewCountCache");

		// tagViewCountCache라는 캐시 객체가 없다면 (첫 단건 조회 시 없다면)
		if (cache == null) {
			return 0;    // 조회수 초기값은 0임 (캐시에서의 조회수는 다르기 때문에 설정해줘야 함)
		}

		Integer originViewCount = cache.get(tagId, Integer.class);

		// 캐시에 데이터가 없으면
		// if (originViewCount == null) {
		// 	return 0;    // 조회수 초기 값은 0임 (캐시에서의 조회수는 다르기 때문에 설정해줘야 함)
		// }
		// 조회수 초기 값은 0임 (캐시에서의 조회수는 다르기 때문에 설정해줘야 함)
		return Objects.requireNonNullElse(originViewCount, 0);    // 해당하는 키에 해당하는 값을 꺼내고, Integer 타입으로 함
	}

	// 캐시 초기화 메서드 (조회수 초기화)
	@Scheduled(cron = "0 0 0 * * *")    // 초 분 시 일 월 요일
	public void clearCacheViewCount() {

		Cache cache = cacheManager.getCache("tagViewCountCache");

		if (cache != null) {
			cache.clear();    // 캐시 초기화
		}
	}

	// 해당 사용자가 태그를 조회할 때마다 조회수를 증가시키지 않도록 하는 메서드
	// public
}
