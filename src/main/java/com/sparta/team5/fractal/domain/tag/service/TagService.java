package com.sparta.team5.fractal.domain.tag.service;

import com.sparta.team5.fractal.common.exception.GlobalException;
import com.sparta.team5.fractal.domain.tag.dto.response.TagProductResponse;
import com.sparta.team5.fractal.domain.tag.dto.response.TagResponse;
import com.sparta.team5.fractal.domain.tag.entity.Tag;
import com.sparta.team5.fractal.domain.tag.exception.TagErrorCode;
import com.sparta.team5.fractal.domain.tag.repository.TagRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TagService implements TagServiceApi {

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
                .orElseThrow(() -> new GlobalException(TagErrorCode.TAG_NOT_FOUND));

        tag.increaseViewCount();

        return TagProductResponse.from(tag);
    }

    @Override
    public Optional<Tag> findByName(String name) {
        return tagRepository.findByName(name);
    }

    @Override
    public Tag createTag(String name) {
        Tag newTag = Tag.from(name);

        return tagRepository.save(newTag);
    }
}
