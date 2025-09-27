package com.sparta.team5.fractal.domain.tag.service;

import com.sparta.team5.fractal.domain.tag.dto.response.TagProductResponse;
import com.sparta.team5.fractal.domain.tag.dto.response.TagResponse;
import com.sparta.team5.fractal.domain.tag.entity.Tag;
import com.sparta.team5.fractal.domain.tag.repository.TagRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public TagProductResponse getTag(Long tagId) {
        Tag tag = tagRepository.findByIdWithProducts(tagId)
                .orElseThrow(() -> new IllegalArgumentException("해당 태그를 찾을 수 없습니다. id: "));

        tag.increaseViewCount();

        return TagProductResponse.from(tag);
    }
}
