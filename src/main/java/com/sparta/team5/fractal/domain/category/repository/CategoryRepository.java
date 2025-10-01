package com.sparta.team5.fractal.domain.category.repository;

import com.sparta.team5.fractal.domain.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    /**
     * 주어진 이름을 가진 카테고리가 존재하는지 확인합니다.
     *
     * @param name 확인할 카테고리 이름
     * @return 해당 이름을 가진 카테고리가 존재하면 {@code true}, 존재하지 않으면 {@code false}
     */
    boolean existsByName(String name);
}
