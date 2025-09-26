package com.sparta.team5.fractal.domain.search.repository;

import com.sparta.team5.fractal.domain.search.entity.Search;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SearchRepository extends JpaRepository<Search, Long> {
}
