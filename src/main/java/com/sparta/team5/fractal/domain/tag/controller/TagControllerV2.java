package com.sparta.team5.fractal.domain.tag.controller;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.team5.fractal.common.response.ApiResponse;
import com.sparta.team5.fractal.domain.tag.dto.response.TagProductResponse;
import com.sparta.team5.fractal.domain.tag.dto.response.TagResponse;
import com.sparta.team5.fractal.domain.tag.service.TagProductServiceV2;
import com.sparta.team5.fractal.domain.tag.service.TagService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/tags")
public class TagControllerV2 {

	private final TagService tagService;
	private final TagProductServiceV2 tagProductServiceV2;

	@GetMapping
	public ResponseEntity<ApiResponse<List<TagResponse>>> getAllTags() {

		List<TagResponse> tags = tagService.getAllTags();
		return ApiResponse.success(tags, "태그 목록을 성공적으로 조회하였습니다.");
	}

	@GetMapping("/{tagId}")
	public ResponseEntity<ApiResponse<TagProductResponse>> getTag
		(
			@PathVariable Long tagId,
			@PageableDefault(size = 30, page = 0, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
		) {

		TagProductResponse tags = tagProductServiceV2.getTag(tagId, pageable);

		return ApiResponse.success(tags, "태그를 조회하였습니다.");
	}
}
