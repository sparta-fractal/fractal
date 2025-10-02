package com.sparta.team5.fractal.domain.category.service;

import com.sparta.team5.fractal.domain.category.entity.Category;

import java.util.Optional;

public interface CategoryServiceApi {

    /**
     * 카테고리 ID로 {@link Category} 엔티티를 조회합니다.
     *
     * @param id 조회할 카테고리 ID
     * @return 해당 ID를 가진 {@link Category}, 존재하지 않으면 {@link Optional#empty()}
     */
    Optional<Category> findById(Long id);
}
