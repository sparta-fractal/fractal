package com.sparta.team5.fractal.domain.product.repository;

import com.sparta.team5.fractal.domain.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("""            
            SELECT p
            FROM Product p
            WHERE (:keyword IS NULL OR p.title LIKE %:keyword% OR p.description LIKE %:keyword%)
                        AND (p.deleted != true)
            ORDER BY p.createdAt DESC
            """)
    Page<Product> findAllByKeyword(Pageable pageable,
                                   @Param("keyword") String keyword);
    
    @Query("""
            SELECT p
            FROM Product p
            JOIN p.productTags pt
            WHERE (pt.tag.id = :tagId)
                        AND (p.deleted = false)
            """)
    Page<Product> findProductsByTagId(@Param("tagId") Long tagId, Pageable pageable);

    @Query("""
            SELECT p
            FROM Product p
            JOIN p.productCategories pt
            WHERE (pt.category.id = :categoryId)
                        AND (p.deleted = false)""")
    Page<Product> findProductsByCategoryId(@Param("categoryId") Long categoryId, Pageable pageable);
}
