package com.sparta.team5.fractal.domain.product.repository;

import com.sparta.team5.fractal.domain.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    // @SQLRestriction으로 자동으로 is_deleted = false 조건이 적용됨
}
