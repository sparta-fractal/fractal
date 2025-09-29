package com.sparta.team5.fractal.domain.search.repository;

import com.sparta.team5.fractal.domain.search.entity.Search;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SearchRepository extends JpaRepository<Search, Long> {

    Search findByKeyword(String keyword);

    @Query("""
            SELECT s.keyword
            FROM Search s
            ORDER BY s.count DESC
            LIMIT 10
            """)
    List<String> getKeywordByCountTopTen();
}
