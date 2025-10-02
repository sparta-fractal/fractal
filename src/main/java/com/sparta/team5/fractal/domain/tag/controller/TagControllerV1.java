package com.sparta.team5.fractal.domain.tag.controller;

import com.sparta.team5.fractal.common.core.dto.ApiResponse;
import com.sparta.team5.fractal.domain.tag.dto.response.TagProductResponse;
import com.sparta.team5.fractal.domain.tag.dto.response.TagResponse;
import com.sparta.team5.fractal.domain.tag.service.TagProductServiceV1;
import com.sparta.team5.fractal.domain.tag.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tags")
public class TagControllerV1 {

    private final TagService tagService;
    private final TagProductServiceV1 tagProductServiceV1;

    @GetMapping
    public ResponseEntity<ApiResponse<List<TagResponse>>> getAllTags() {

        List<TagResponse> tags = tagService.getAllTags();

        return ApiResponse.success(tags, "태그 목록을 성공적으로 조회하였습니다.");
    }

    @GetMapping("/{tagId}")
    public ResponseEntity<ApiResponse<TagProductResponse>> getProductsByTagId(
            @PathVariable Long tagId,
            @PageableDefault(size = 30, page = 0, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {

        TagProductResponse tags = tagProductServiceV1.getProductsByTagId(tagId, pageable);

        return ApiResponse.success(tags, "해당 태그가 적용된 게시글을 조회하였습니다.");
    }
}
