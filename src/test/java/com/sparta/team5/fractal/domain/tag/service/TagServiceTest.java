package com.sparta.team5.fractal.domain.tag.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sparta.team5.fractal.domain.tag.dto.response.TagResponse;
import com.sparta.team5.fractal.domain.tag.entity.Tag;
import com.sparta.team5.fractal.domain.tag.repository.TagRepository;

@ExtendWith(MockitoExtension.class)
public class TagServiceTest {

	@Mock
	private TagRepository tagRepository;

	@InjectMocks
	private TagService tagService;

	@Test
	@DisplayName("태그 목록 조회 성공")
	void getAllTags_태그가_존재한다면_태그목록을_반환할_수_있다() {

		// given
		Tag tag1 = Tag.from("교재");
		Tag tag2 = Tag.from("상태좋음");
		Tag tag3 = Tag.from("쿨거래");

		given(tagRepository.findAll()).willReturn(List.of(tag1, tag2, tag3));

		// when
		List<TagResponse> tags = tagService.getAllTags();

		// then
		assertThat(tags).hasSize(3)
			.extracting(TagResponse::name)
			.containsExactlyInAnyOrder("교재", "상태좋음", "쿨거래");
	}
}
