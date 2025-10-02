package com.sparta.team5.fractal.domain.search.repository;

import com.sparta.team5.fractal.domain.search.entity.Search;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SearchRepository extends JpaRepository<Search, Long> {

    /**
     * 특정 키워드로 검색 엔티티를 조회합니다.
     *
     * @param keyword 조회할 검색 키워드
     * @return 해당 키워드를 가진 {@link Search} 엔티티 (없을 경우 null 반환)
     */
    Search findByKeyword(String keyword);

    /**
     * 검색 횟수(count)가 많은 순으로 키워드를 조회합니다.
     * Pageable의 page, size를 이용하여 상위 10개를 가져올 수 있습니다.
     *
     * @param pageable 페이징 정보
     * @return 검색 횟수가 많은 순으로 정렬된 10개의 키워드 목록
     */
    @Query("""
            SELECT s.keyword
            FROM Search s
            ORDER BY s.count DESC
            """)
    List<String> getKeywordByCountTopTen(Pageable pageable);
}