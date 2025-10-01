package com.sparta.team5.fractal.domain.tag.service;

import com.sparta.team5.fractal.domain.tag.entity.Tag;

import java.util.Optional;

public interface TagServiceApi {

    /**
     * 태그 이름으로 {@link Tag} 엔티티를 조회합니다.
     *
     * @param name 조회할 태그 이름
     * @return 해당 이름을 가진 {@link Tag}, 존재하지 않으면 {@link Optional#empty()}
     */
    Optional<Tag> findByName(String name);

    /**
     * 새로운 {@link Tag} 엔티티를 생성합니다.
     * <p>
     * 동일한 이름의 태그가 이미 존재하는 경우 예외를 발생시킬 수 있습니다.
     *
     * @param name 생성할 태그 이름
     * @return 생성된 {@link Tag} 엔티티
     */
    Tag createTag(String name);

    /**
     * 태그 ID로 {@link Tag} 엔티티를 조회합니다.
     *
     * @param tagId 조회할 태그 ID
     * @return 해당 ID를 가진 {@link Tag}, 존재하지 않으면 {@link Optional#empty()}
     */
    Optional<Tag> findById(Long tagId);
}
