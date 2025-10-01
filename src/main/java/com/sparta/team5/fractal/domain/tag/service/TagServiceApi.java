package com.sparta.team5.fractal.domain.tag.service;

import java.util.Optional;

import com.sparta.team5.fractal.domain.tag.entity.Tag;

public interface TagServiceApi {

	Optional<Tag> findByName(String name);

	Tag createTag(String name);

	Optional<Tag> findById(Long tagId);
}
