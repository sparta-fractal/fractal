package com.sparta.team5.fractal.domain.category.service;

import com.sparta.team5.fractal.domain.category.entity.Category;
import java.util.Optional;

public interface CategoryServiceApi {
    Optional<Category> findById(Long id);

}
