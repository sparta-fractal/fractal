package com.sparta.team5.fractal.domain.category.repository;

import com.sparta.team5.fractal.domain.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByName(String name);

}
