package com.sparta.team5.fractal.domain.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sparta.team5.fractal.domain.category.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
