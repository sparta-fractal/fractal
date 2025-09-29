package com.sparta.team5.fractal.domain.tag.service;

import com.sparta.team5.fractal.domain.tag.entity.Tag;
import java.util.Optional;

public interface TagServiceApi {

    Optional<Tag> findByName(String name);

    Tag createTag(String name);
}
