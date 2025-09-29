package com.sparta.team5.fractal.domain.search.service;

import java.util.List;

public interface SearchServiceApi {

    /*
        이걸 product service에서 하면 됌
        1. 서치하고 이 서치를 데이터베이스(Searches) 조회 이후 있다면 increase 없다면 데이터베이스에 저장
        2. 해당 키워드로 Like를 통해서 데이터베이스(Products) 조회
        3. 조회값 반환
    */
    // 서치를 존재 여부 판단 있다면 조회 증가 후 true 반환, 없다면 false 반환(product 로직에서 서치 저장 필요)
    boolean existAndIncrease(String keyword);

    // 서치 저장
    void createSearch(String keyword);

    // 검색 수 많은대로 10위까지 검색어 가져오기
    List<String> getTopTenKeywords();
}
