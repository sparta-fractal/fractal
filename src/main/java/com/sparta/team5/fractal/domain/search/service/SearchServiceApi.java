package com.sparta.team5.fractal.domain.search.service;

import java.util.List;

public interface SearchServiceApi {

    /*
        이걸 product service에서 하면 됌
        1. 서치하고 이 서치를 데이터베이스(Searches) 조회 이후 있다면 increase 없다면 데이터베이스에 저장
        2. 해당 키워드로 Like를 통해서 데이터베이스(Products) 조회
        3. 조회값 반환
    */

    /**
     * 주어진 검색 키워드가 존재하는지 확인하고,
     * 존재한다면 해당 키워드의 조회수를 증가시킵니다.
     * <p>
     * 존재하지 않는 경우에는 {@code false}를 반환하며,
     * 필요 시 Product 서비스에서 새로운 검색 키워드를 저장해야 합니다.
     *
     * @param keyword 확인할 검색 키워드
     * @return 키워드가 존재하여 조회수를 증가시켰다면 {@code true}, 존재하지 않으면 {@code false}
     */
    boolean existAndIncrease(String keyword);

    /**
     * 새로운 검색 키워드를 저장합니다.
     * <p>
     * 이미 존재하는 키워드는 {@link #existAndIncrease(String)} 메서드를 통해 조회수를 증가시켜야 합니다.
     *
     * @param keyword 저장할 검색 키워드
     */
    void createSearch(String keyword);

    /**
     * 검색 횟수가 많은 순으로 최대 10개의 인기 검색어를 조회합니다.
     *
     * @return 검색 횟수 상위 10개의 키워드 목록
     */
    List<String> getTopTenKeywords();
}
