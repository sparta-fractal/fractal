package com.sparta.team5.fractal.domain.tag.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sparta.team5.fractal.domain.tag.dto.response.TagResponse;
import com.sparta.team5.fractal.domain.tag.entity.Tag;
import com.sparta.team5.fractal.domain.tag.repository.TagRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TagService {

	private final TagRepository tagRepository;

	@Transactional(readOnly = true)
	public List<TagResponse> getAllTags() {

		List<Tag> tags = tagRepository.findAll();

		return tags.stream()
			.map(TagResponse::from)
			.collect(Collectors.toList());
	}
}
