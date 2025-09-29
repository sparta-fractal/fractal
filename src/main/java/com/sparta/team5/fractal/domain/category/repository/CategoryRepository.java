package com.sparta.team5.fractal.domain.category.repository;

import com.sparta.team5.fractal.domain.category.entity.Category;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByName(String name);


    @Query("SELECT c FROM Category c " +
            "LEFT JOIN FETCH c.productCategories pc " +
            "LEFT JOIN FETCH pc.product p " +
            "WHERE c.id = :categoryId")
    Optional<Category> findByIdWithProducts(@Param("categoryId") Long categoryId);
}
