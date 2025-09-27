package com.sparta.team5.fractal.domain.product.repository;

import com.sparta.team5.fractal.domain.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, Long> {
    // @SQLRestriction으로 자동으로 is_deleted = false 조건이 적용됨

    @Query("""            
            SELECT p
            FROM Product p
            WHERE :keyword IS NULL OR p.title LIKE %:keyword% OR p.description LIKE %:keyword%
            ORDER BY p.createdAt DESC
            """)
    Page<Product> findAllByKeyword(Pageable pageable,
                                   @Param("keyword") String keyword);
}
