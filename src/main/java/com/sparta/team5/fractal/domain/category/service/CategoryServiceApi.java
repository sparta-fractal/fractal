package com.sparta.team5.fractal.domain.category.service;

import com.sparta.team5.fractal.domain.category.entity.Category;
import java.util.Optional;
import org.springframework.data.repository.query.Param;

public interface CategoryServiceApi {
    Optional<Category> findById(Long id);

    Optional<Category> findByIdWithProducts(@Param("categoryId") Long categoryId);
}
