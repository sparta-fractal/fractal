package com.sparta.team5.fractal.domain.tag.repository;

import com.sparta.team5.fractal.domain.tag.entity.Tag;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TagRepository extends JpaRepository<Tag, Long> {

    Optional<Tag> findByName(String name);

    @Query("""
            SELECT DISTINCT t
            FROM Tag t
            LEFT JOIN FETCH t.productTags pt
            LEFT JOIN FETCH pt.product p
            WHERE t.id = :tagId
              AND (p.deleted = false OR p IS NULL)
            """)
    Optional<Tag> findByIdWithProducts(@Param("tagId") Long tagId);
}


