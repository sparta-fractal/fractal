package com.sparta.team5.fractal.domain.tag.service;

import com.sparta.team5.fractal.domain.tag.dto.response.TagResponse;
import com.sparta.team5.fractal.domain.tag.entity.Tag;
import com.sparta.team5.fractal.domain.tag.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagService implements TagServiceApi {

    private final TagRepository tagRepository;

    @Transactional(readOnly = true)
    public List<TagResponse> getAllTags() {

        List<Tag> tags = tagRepository.findAll();

        List<TagResponse> response = tags.stream()
                .map(TagResponse::from)
                .collect(Collectors.toList());

        return response;
    }

    @Override
    public Optional<Tag> findByName(String name) {

        Optional<Tag> response = tagRepository.findByName(name);

        return response;
    }

    @Override
    public Tag createTag(String name) {

        Tag newTag = Tag.from(name);

        tagRepository.save(newTag);

        return newTag;
    }

    @Override
    public Optional<Tag> findById(Long tagId) {

        Optional<Tag> response = tagRepository.findById(tagId);

        return response;
    }
}