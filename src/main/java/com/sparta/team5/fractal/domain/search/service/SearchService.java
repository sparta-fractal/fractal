package com.sparta.team5.fractal.domain.search.service;

import com.sparta.team5.fractal.domain.search.entity.Search;
import com.sparta.team5.fractal.domain.search.repository.SearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class SearchService implements SearchServiceApi {

    private final SearchRepository searchRepository;

    @Override
    public boolean existAndIncrease(String searchKeyword) {

        Search keyword = searchRepository.findByKeyword(searchKeyword);

        if (keyword != null) {
            keyword.increaseCount();
            return true;
        }

        return false;
    }

    @Override
    public void createSearch(String searchKeyword) {

        Search search = Search.of(searchKeyword);

        searchRepository.save(search);
    }

    @Override
    public List<String> getTopTenKeywords() {

        Pageable pageable = PageRequest.of(0, 10);

        List<String> keywords = searchRepository.getKeywordByCountTopTen(pageable);

        return keywords;
    }
}
