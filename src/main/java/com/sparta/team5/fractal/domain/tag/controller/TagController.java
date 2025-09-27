package com.sparta.team5.fractal.domain.tag.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.team5.fractal.common.response.ApiResponse;
import com.sparta.team5.fractal.domain.tag.dto.response.TagResponse;
import com.sparta.team5.fractal.domain.tag.service.TagService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tags")
public class TagController {

	private final TagService tagService;

	@GetMapping
	public ResponseEntity<ApiResponse<List<TagResponse>>> getAllTags() {

		List<TagResponse> tags = tagService.getAllTags();

		return ApiResponse.success(tags, "태그 목록을 성공적으로 조회하였습니다.");
	}
}
