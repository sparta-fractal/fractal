package com.sparta.team5.fractal.domain.tag.dto.response;

import com.sparta.team5.fractal.domain.tag.entity.Tag;

public record TagResponse(Long id,
						  String name,
						  Long views) {

	public static TagResponse from(Tag tag) {

		return new TagResponse(
			tag.getId(),
			tag.getName(),
			tag.getViews()
		);
	}
}
